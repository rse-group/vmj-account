module accountpl.product.basic {
   requires vmj.object.mapper;
   requires vmj.routing.route;
   requires vmj.hibernate.integrator;


        // need this to run hibernate
    requires net.bytebuddy;
    requires java.xml.bind;
    requires com.sun.xml.bind;
    requires com.fasterxml.classmate;
    // https://stackoverflow.com/questions/65171364/java-and-hibernate-error-could-not-instantiate-persister-org-hibernate-persiste
    requires jdk.unsupported;

    requires accountpl.account.core;

    
    requires prices.auth.vmj;
    requires prices.auth.vmj.model;

}