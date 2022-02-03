package com.inspien.pilot.manager;

import org.junit.Before;
import org.junit.Test;

public class BundleSecurityManagerTest {
    private final static String policyFilePath = "src/test/resources/security.policy";
    private final static String deployPath = "src/test/resources/deploy";
//    private BundleSecurityManager bundleSecurityManager;
//    private final BundleContext context = FrameworkUtil.getBundle(BundleSecurityManagerTest.class).getBundleContext();

    @Before
    public void init() throws Exception {
//        System.out.println(context);
    }


    @Test
    public void generatePolicyTest() {
//        BundlePolicy policy = new BundlePolicy("ALLOW", "FilePermission",
//                "config-repository/ws/pkg/*", "READ");
    }
}
