module vmj.hibernate.integrator {
    exports vmj.hibernate.integrator;

    requires org.hibernate.orm.core;
    // https://stackoverflow.com/questions/46488346/error32-13-error-cannot-access-referenceable-class-file-for-javax-naming-re/50568217
    requires java.naming;
}