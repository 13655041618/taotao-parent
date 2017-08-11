# taotao-parent
淘淘商城_SSM框架
此项目为Spring + Spring MVC + mybatis整合项目，项目中使用到了solr搜索服务 + redis缓存服务，因此需要配置redis集群服务器和solr服务器。
其中redis配置文件位于taotao-order/resource/spring/applicationContext-service.xml中，按照配置文件中所需配置ip和端口即可。
solr配置文件位于taotao-search/resource/spring/applicationContext-service.xml中，只需要配置相关solr服务器即可。
项目中所有web打包的子工程已配置好tomcat插件，启动tomcat插件即可启动项目。
