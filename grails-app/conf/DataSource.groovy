dataSource {
    pooled = true
	driverClassName = "com.mysql.jdbc.Driver"
    /*jmxExport = true
    driverClassName = "org.h2.Driver"
    username = "sa"
    password = ""*/
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = false
//    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory' // Hibernate 3
    cache.region.factory_class = 'org.hibernate.cache.ehcache.EhCacheRegionFactory' // Hibernate 4
    singleSession = true // configure OSIV singleSession mode
}

// environment specific settings
environments {
    development {
        dataSource {
			username = "root"
			password = "root"
			dbCreate = "update"
			url = "jdbc:mysql://localhost:3306/message_system"
		}
    }
    test {
        dataSource {
            dbCreate = "update"
            //url = "jdbc:h2:mem:testDb;MVCC=TRUE;LOCK_TIMEOUT=10000;DB_CLOSE_ON_EXIT=FALSE"
			url = "jdbc:mysql://localhost:3306/message_system"
			username = "root"
			password = "root"
        }
    }
    production {
        dataSource {
            dbCreate = "update"
            //url = "jdbc:h2:prodDb;MVCC=TRUE;LOCK_TIMEOUT=10000;DB_CLOSE_ON_EXIT=FALSE"
			url = "jdbc:mysql://localhost:3306/message_system"
			username = "root"
			password = "root"
            
        }
    }
}
