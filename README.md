# taotao-parent
淘淘商城_SSM框架<br>
此项目为Spring + Spring MVC + mybatis整合项目，项目中使用到了solr搜索服务 + redis缓存服务，因此需要配置redis集群服务器和solr服务器。<br>
运行前需要先使用数据库数据.sql文件导入数据到数据库。<br>
其中redis配置文件位于`taotao-order/resource/spring/applicationContext-service.xml`中，按照配置文件中所需配置ip和端口即可。<br>
如果要使用solr服务，solr配置文件位于`taotao-search/resource/spring/applicationContext-service.xml`中，只需要配置相关solr服务器即可。<br>
此项目中通过直接访问mybatis实现了简单的搜索服务，因此不使用solr服务也可以进行搜索。<br>
项目中所有web打包的子工程已配置好tomcat插件，启动tomcat插件即可启动项目。
