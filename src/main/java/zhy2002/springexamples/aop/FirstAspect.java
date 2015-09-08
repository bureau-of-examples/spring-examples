package zhy2002.springexamples.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class FirstAspect {


    @Before("execution(* print(..))")     // or @Around("com.xyz.myapp.SystemArchitecture.businessService()") where businessService is a annotated pointcut
    public void beforePrint() {
        System.out.println("Before advice from obj " + this.hashCode());
    }

    @After("execution(* print(..))")
    public void afterPrint() {
        System.out.println("After advice from obj " + this.hashCode());

    }

}
