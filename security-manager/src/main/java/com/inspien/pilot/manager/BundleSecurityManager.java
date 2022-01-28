package com.inspien.pilot.manager;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.condpermadmin.ConditionInfo;
import org.osgi.service.condpermadmin.ConditionalPermissionAdmin;
import org.osgi.service.condpermadmin.ConditionalPermissionInfo;
import org.osgi.service.condpermadmin.ConditionalPermissionUpdate;
import org.osgi.service.permissionadmin.PermissionInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class BundleSecurityManager {
    private Logger logger = LoggerFactory.getLogger(BundleSecurityManager.class);
    private ConditionalPermissionAdmin cpa;
    private ConditionalPermissionUpdate u;
    private static final String BND_LOCATION_CONDITION = "org.osgi.service.condpermadmin.BundleLocationCondition";
    private static final String FILE_SCHEME = "file:/";
    private static final String ACCESS_TYPE_ALLOW = "allow";
    private static final String ACCESS_TYPE_DENY = "deny";
    private static final String NAME_DELIMITER = "@";
    private File policyFile;
    private String deployPath;

    public BundleSecurityManager(BundleContext context, String policyPath, String deployPath) throws Exception {
        ServiceReference ref = context.getServiceReference(ConditionalPermissionAdmin.class.getName());
        cpa = (ConditionalPermissionAdmin) context.getService(ref);
        this.deployPath = deployPath;
        this.policyFile = new File(policyPath);
        if (!policyFile.exists())
            throw new Exception("No policy file at: " + policyFile.getAbsolutePath());
        apply(readPolicyFile());
    }

    public void addBundlePolicy(String bundleId, List<PolicyEntry> policyList) throws Exception {
        List<PolicyEntry> denyPolicyList = policyList.stream().filter(p -> p.getAccessType().equals(ACCESS_TYPE_DENY)).collect(Collectors.toList());
        List<PolicyEntry> allowPolicyList = policyList.stream().filter(p -> p.getAccessType().equals(ACCESS_TYPE_ALLOW)).collect(Collectors.toList());

        List<ConditionalPermissionInfo> infoList = readPolicyFile();
        try {
            List<ConditionalPermissionInfo> changedList = new ArrayList<>();
            changedList.addAll(infoList);
            if(denyPolicyList.size() > 0)
                changedList.add(0, generateConditionalPermissionInfo(bundleId, ACCESS_TYPE_DENY, denyPolicyList));
            if(allowPolicyList.size() > 0)
                changedList.add(0, generateConditionalPermissionInfo(bundleId, ACCESS_TYPE_ALLOW, allowPolicyList));

            writePolicyFile(changedList);
            apply(changedList);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            writePolicyFile(infoList);
        }
    }

    private ConditionalPermissionInfo generateConditionalPermissionInfo(String bundleId, String accessType, List<PolicyEntry> policyList) {
        List<PermissionInfo> permInfoList = new ArrayList<>();
        for(PolicyEntry policy : policyList)
            permInfoList.add(new PermissionInfo(policy.getPermissionType(), policy.getPermissionName(), policy.getPermissionAction()));

        String[] s = { FILE_SCHEME + deployPath + "/" + bundleId };
        List<ConditionInfo> condInfoList = new ArrayList<>();
        condInfoList.add(new ConditionInfo(BND_LOCATION_CONDITION, s));

        return cpa.newConditionalPermissionInfo(
                bundleId + NAME_DELIMITER + accessType,
                condInfoList.toArray(new ConditionInfo[condInfoList.size()]),
                permInfoList.toArray(new PermissionInfo[permInfoList.size()]),
                accessType);
    }

    public void removeBundlePolicy(String bundleId) throws Exception {
        List<ConditionalPermissionInfo> infoList = readPolicyFile();
        try {
            List<ConditionalPermissionInfo> changedList = new ArrayList<>();
            changedList.addAll(infoList);
            for(Iterator<ConditionalPermissionInfo> iter = changedList.iterator(); iter.hasNext(); ) {
                ConditionalPermissionInfo info = iter.next();
                if(info.getName().split(NAME_DELIMITER)[0].equals(bundleId)) {
                    iter.remove();
                    logger.info("bundle '" + bundleId + "' policy removed.");
                }
            }
            writePolicyFile(changedList);
            apply(changedList);
        } catch (Exception e) {
            writePolicyFile(infoList);
        }
    }

    private void apply(List<ConditionalPermissionInfo> infoList) throws RuntimeException {
        u = cpa.newConditionalPermissionUpdate();
        u.getConditionalPermissionInfos().clear();
        u.getConditionalPermissionInfos().addAll(infoList);

        if (!u.commit())
            throw new ConcurrentModificationException("Permissions changed during update");
        String content = "\n";
        for (ConditionalPermissionInfo i : u.getConditionalPermissionInfos())
            content += i + "\n\n";
        logger.info(content);
        logger.info("Conditional Permission Updated..");
    }

    private List<ConditionalPermissionInfo> readPolicyFile() throws Exception {
        try (BufferedReader policyReader = new BufferedReader(new FileReader(policyFile))) {
            List<ConditionalPermissionInfo> infoList = new ArrayList<>();
            StringBuffer buffer = new StringBuffer();
            for (String input = policyReader.readLine(); input != null; input = policyReader.readLine()) {
                if (!input.trim().startsWith("#")) {
                    buffer.append(input);
                    if (input.contains("}")) {
                        infoList.add(cpa.newConditionalPermissionInfo(buffer.toString()));
                        buffer = new StringBuffer();
                    }
                }
            }
            return infoList;
        }
    }

    private void writePolicyFile(List<ConditionalPermissionInfo> infoList) throws IOException {
        String policyContent = "";
        for(ConditionalPermissionInfo info : infoList)
            policyContent += info.getEncoded() + "\n";
        FileUtil.writeToFile(policyFile, policyContent);
    }
}
