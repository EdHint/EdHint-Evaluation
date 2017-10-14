import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import sketch4j.executor.ExecutorType;
import sketch4j.executor.SketchExecutor;
import sketch4j.request.invocation.BatchInvocationRequest;

public class Main {

    private static BatchInvocationRequest request = new BatchInvocationRequest();



    public static java.lang.String getTitle(java.lang.String arg0) {
        return (java.lang.String) request.newBatchInvocation(0)
                 .addDeclaringClassesFromPrefix("org.jsoup")
                 .addDeclaringClasses(java.lang.String.class)
                 .addArgument(java.lang.String.class, "arg0", arg0)
                 .setReturnType(java.lang.String.class)
                 .setMaxSearchDepth(3)
                 .invoke();
    }


    public static boolean test() throws Throwable {
        java.lang.String url = "https://www.google.com/";
        java.lang.String title = getTitle(url);
        if ("Google".equals(title))
            return true;
        else 
            return false;
    }
     
    @Test(timeout=5000)
    public void t() throws Throwable {
        assertTrue(test());
    } 


    public static void main(String[] args) {
        int maxTime = Integer.parseInt(args[0]);
        SketchExecutor.setType(ExecutorType.JUZI);
        JUnitCore core = new JUnitCore();
        boolean found = false;
        boolean timeout = false;
        int i = 0;
        long begin = System.currentTimeMillis();
        do {
            if(System.currentTimeMillis() - begin >= maxTime * 1000) {
                timeout = true;
                break;
            }
            i ++;
            request.reset();
            
            Result res =core.run(Main.class);
            if(res.wasSuccessful()) {
                found = true;
                break;
            }
            
        } while (SketchExecutor.incrementCounter());
        if(found) {
            System.out.println("Found solution:");
            System.out.println("----------------------------------------------------------------");
            System.out.println(request.getCandidateBatchInvocation(0));
            System.out.println("----------------------------------------------------------------");
        } else if(timeout) {
            System.out.println("Timeout! Max execution time: " + maxTime + " seconds.");
        } else {
            System.out.println("No solution!");
        }
        
        System.out.println("Number of invocations tried: " + request.getNumBatchInvocationsTried(0));
        System.out.println("Search depth: " + request.getCurrentSearchDepth(0));
        System.out.println("Number of executions: " + i);
        
        System.exit(0);
    }

}
