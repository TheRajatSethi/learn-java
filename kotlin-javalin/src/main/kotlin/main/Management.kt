package main;

import javax.management.*;
import java.lang.management.ManagementFactory;

/**
 * ```
 * Checkout `jmxterm` for running via console
 */

interface ManagementMBean {
    fun doSomething();
}


class Management : ManagementMBean {
    override fun doSomething() {
        println("Doing something in management MBean method do something");
    }
}

fun register(){
    var objectName:ObjectName? = null;
    try {
        objectName = ObjectName("com.ftft:type=basic,name=game”");
        val server = ManagementFactory.getPlatformMBeanServer();
        server.registerMBean(Management(), objectName);
    } catch (e: Exception){
        println(e.message)
    }
}
