### OSGI R7 Conditinal Admin Permission
#### Apache Karaf 4.3.6 ver
---
bundle security pilot

###  Getting Started

```shell
# 1. <karaf_base>/etc/system.properties > uncomment line 137,138
# 2. install framework-security feature and restart karaf.
feature:install framework-security

# 3. permission bug fix
# ...apache-karaf-4.3.6-1\data\cache\bundle12\version0.0\bundle.jar
# modify OSGI-INF/permissions.perm

# framework-security Permission
(java.util.PropertyPermission "*" "read")
(java.lang.RuntimePermission "*")

# 4. copy security.policy file to '<karaf_base>/etc' path.
# 5. install bundle
bundle:install mvn:com.inspien.pilot/security-api/1.0-SNAPSHOT
bundle:install mvn:com.inspien.pilot/security-manage/1.0-SNAPSHOT

# 6. install security-pkg bundle as hot deploy.
# copy security-pkg-1.0-SNAPSHOT.jar file to <karaf_base>/deploy path

```



