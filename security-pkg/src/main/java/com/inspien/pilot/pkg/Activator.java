package com.inspien.pilot.pkg;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

    @Override
    public void start(BundleContext context) throws Exception {
        System.out.println("started...");
//        System.getSecurityManager().checkPermission(new FilePermission("/config-repository/test.txt", "read"));

//        package resource access
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

//        allow path
//        File ff = new File("config-repository/test.txt");
//        DataInputStream dinn = new DataInputStream(new FileInputStream(ff));
//        byte[] buff = new byte[(int) ff.length()];
//        try {
//            dinn.readFully(buff);
//            System.out.println("config :: " + new String(buff));
//        } finally {
//            dinn.close();
//        }

//        deny path
//        File f = new File("test.txt");
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
