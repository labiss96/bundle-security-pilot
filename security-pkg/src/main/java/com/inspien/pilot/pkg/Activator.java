package com.inspien.pilot.pkg;

import org.apache.felix.framework.security.util.LocalPermissions;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.condpermadmin.ConditionalPermissionAdmin;
import org.osgi.service.condpermadmin.ConditionalPermissionUpdate;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilePermission;

public class Activator implements BundleActivator {

    @Override
    public void start(BundleContext context) throws Exception {
        System.out.println("started...");
//        ServiceReference ref = context.getServiceReference(ConditionalPermissionAdmin.class.getName());
//        ConditionalPermissionAdmin conditionalPermissionAdmin = (ConditionalPermissionAdmin) context.getService(ref);
//        conditionalPermissionAdmin.
//        ConditionalPermissionUpdate u = conditionalPermissionAdmin.newConditionalPermissionUpdate();
//        System.out.println(u.getConditionalPermissionInfos());

        System.getSecurityManager().checkPermission(new FilePermission("/config-repository/test.txt", "read"));
        System.out.println("allowed");
//
//        InputStream in = Activator.class.getResourceAsStream("/rsc-test.txt");
//        byte[] content;
//        try {
//            byte[] buf = new byte[2048];
//            int nread = -1;
//            ByteArrayOutputStream out = new ByteArrayOutputStream();
//            while ((nread = in.read(buf)) != -1)
//                out.write(buf, 0, nread);
//            content = out.toByteArray();
//            System.out.println("content :: " + new String(content));
//        } finally {
//            in.close();
//        }
//
//
//        File f = new File("config-repository/test.txt");
//        DataInputStream din = new DataInputStream(new FileInputStream(f));
//        byte[] buf = new byte[(int) f.length()];
//        try {
//            din.readFully(buf);
//            System.out.println("txt :: " + new String(buf));
//        } finally {
//            din.close();
//        }
    }

    @Override
    public void stop(BundleContext context) throws Exception {

    }
}
