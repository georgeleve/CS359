package mainClasses;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

@javax.ws.rs.ApplicationPath("")
public class ApplicationConfig extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    @Override
    public Set<Object> getSingletons() {
        Set<Object> set = new HashSet<>();
        set.add(new BloodTestAPI());
        return set;
    }

    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(BloodTestAPI.class);
    }
}