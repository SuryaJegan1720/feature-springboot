package org.arpit.java2blog.aop;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class LoggingAspect {
	
	
	  @Autowired private JavaMailSender emailSender;
	 
	
	private static final Logger LOGGER = LogManager.getLogger(LoggingAspect.class);
	 
    //AOP expression for which methods shall be intercepted
    @Around("execution(* org.arpit.java2blog.dao.CustomerDaoImpl.addCustomer(*))")
    public Object profileAllMethods(ProceedingJoinPoint proceedingJoinPoint) throws Throwable 
    {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
         
        //Get intercepted method details
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();
         
        final StopWatch stopWatch = new StopWatch();
         
        //Measure method execution time
        stopWatch.start();
        Object result = proceedingJoinPoint.proceed();
        stopWatch.stop();
 
        //Log method execution time
        LOGGER.info("Execution time of " + className + "." + methodName + " :: " + stopWatch.getTotalTimeMillis() + " ms");
        sendSimpleMessage("suryajegan619@gmail.com","Customer Registered","Customer Added Successfully!!!");
        //sendSimpleMessage("vino.ruah@gmail.com","Performance Test","Execution time of " + className + "." + methodName + " :: " + stopWatch.getTotalTimeMillis() + " ms");
        return result;
    }
    
    
	
	  public void sendSimpleMessage(String to, String subject, String text) {
	  SimpleMailMessage message = new SimpleMailMessage();
	  message.setFrom("systemnorevert@gmail.com"); message.setTo(to);
	  message.setSubject(subject); message.setText(text);
	  emailSender.send(message); 
	  }
	 

}
