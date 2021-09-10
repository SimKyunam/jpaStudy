package com.jpa.demo.jpaorm.procedure;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProcedureTest {

    @PersistenceContext
    private EntityManager em;

    @DisplayName("1. 순서기반 프로시져")
    @Test
    void test_1(){
        StoredProcedureQuery spq =
                em.createStoredProcedureQuery("proc_multiply"); //Hello
        spq.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
        spq.registerStoredProcedureParameter(2, Integer.class, ParameterMode.OUT);

        spq.setParameter(1, 100);
        spq.execute();

        Integer out = (Integer) spq.getOutputParameterValue(2);
        System.out.println("out = " + out);
    }

    @DisplayName("2. 파라미터 이름 사용")
    @Test
    void test_2(){
        StoredProcedureQuery spq =
                em.createStoredProcedureQuery("proc_multiply"); //Hello
        spq.registerStoredProcedureParameter("inParam", Integer.class, ParameterMode.IN);
        spq.registerStoredProcedureParameter("outParam", Integer.class, ParameterMode.OUT);

        spq.setParameter(1, 100);
        spq.execute();

        Integer out = (Integer) spq.getOutputParameterValue("outParam");
        System.out.println("out = " + out);
    }

    @DisplayName("3. xml 프로시저 사용(procedure.xml)")
    @Test
    void test_3(){
        StoredProcedureQuery spq =
//                em.createStoredProcedureQuery("multiplyXml");
                em.createStoredProcedureQuery("multiply");
        spq.setParameter("inParam", 100);
        spq.execute();

        Integer out = (Integer) spq.getOutputParameterValue("outParam");
        System.out.println("out = " + out);
    }


}
