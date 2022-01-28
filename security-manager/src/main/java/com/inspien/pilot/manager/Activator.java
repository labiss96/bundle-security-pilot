package com.inspien.pilot.manager;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import java.io.File;

public class Activator implements BundleActivator {

    public void start(BundleContext context) throws Exception {
        {
            String configPath = System.getProperty("karaf.etc");
            String policyPath = configPath + File.separator + "security.policy";
            String deployPath = "C:/workspace/inspien/cloud-edi/work/package-security/apache-karaf-4.3.6/deploy";

            bundleSecurityManager = new BundleSecurityManager(context, policyPath, deployPath);

//            PolicyEntry policy = new PolicyEntry("allow", "java.io.FilePermission",
//                    "config-repository/*", "read");
//            PolicyEntry policy2 = new PolicyEntry("allow", "java.io.FilePermission",
//                    "*", "read");
//            bundleSecurityManager.addBundlePolicy("bnd", Arrays.asList(policy, policy2));
//            bundleSecurityManager.removeBundlePolicy("bnd");
        }
    }

    private BundleSecurityManager bundleSecurityManager;

    public void stop(BundleContext context) throws Exception {
    }
}
