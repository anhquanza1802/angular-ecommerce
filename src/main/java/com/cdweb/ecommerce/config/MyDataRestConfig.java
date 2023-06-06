package com.cdweb.ecommerce.config;

import com.cdweb.ecommerce.entity.Country;
import com.cdweb.ecommerce.entity.Product;
import com.cdweb.ecommerce.entity.ProductCategory;
import com.cdweb.ecommerce.entity.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {
    private EntityManager entityManager;
    @Autowired
    public MyDataRestConfig(EntityManager theEntityManager){
        entityManager = theEntityManager;
    }
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        HttpMethod[] theUnsupportedActions = {HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE};
        //ngắt kết nối phương thức http của Product : put, post and delete
        disableHttpMethods(Product.class,config, theUnsupportedActions);
        //ngắt kết nối phương thức http của ProductCategory : put, post and delete
        disableHttpMethods(ProductCategory.class,config, theUnsupportedActions);

        disableHttpMethods(Country.class,config, theUnsupportedActions);
        disableHttpMethods(State.class,config, theUnsupportedActions);
        // phương thức bổ trợ
        exposeIds(config);
    }

    private static void disableHttpMethods(Class theClass,RepositoryRestConfiguration config, HttpMethod[] theUnsupportedActions) {
        config.getExposureConfiguration()
                .forDomainType(theClass)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions));
    }

    private void exposeIds(RepositoryRestConfiguration config) {
        // hiển thị trường dữ liệu ids của Category


        // lấy 1 list tất cả các entity classes của entity manager

        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();
        // tạo mảng với entity types
        List<Class> entityClasses = new ArrayList<>();
        //lấy entity types for the entities

        for (EntityType tempEntityType : entities){
            entityClasses.add(tempEntityType.getJavaType());
        }
        Class[] domainTypes = entityClasses.toArray(new Class[0]);
        config.exposeIdsFor(domainTypes);

    }


}
