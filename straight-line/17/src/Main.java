import sketch4j.executor.ExecutorType;
import sketch4j.executor.SketchExecutor;
import sketch4j.request.invocation.BatchInvocationRequest;

public class Main {

    private static BatchInvocationRequest request = new BatchInvocationRequest();



    public static int daysBetween(org.joda.time.DateTime arg0, org.joda.time.DateTime arg1) {
        return (Integer) request.newBatchInvocation(0)
                 .addDeclaringClassesFromPrefix("org.joda.time")
                 .addDeclaringClasses(org.joda.time.DateTime.class)
                 .addArgument(org.joda.time.DateTime.class, "arg0", arg0)
                 .addArgument(org.joda.time.DateTime.class, "arg1", arg1)
                 .setReturnType(int.class)
                 .setMaxSearchDepth(4)
                 .invoke();
    }


    public static boolean test0() throws Throwable {
    		
        org.joda.time.DateTimeZone PORTUGAL = org.joda.time.DateTimeZone.forID("Europe/Lisbon");
        org.joda.time.DateTime start = new org.joda.time.DateTime(2013, 9, 16, 5, 0, 0, PORTUGAL);
        org.joda.time.DateTime end = new org.joda.time.DateTime(2013, 10, 21, 13, 0, 0, PORTUGAL);
    		
        if (daysBetween(start, end) == 35)
    	return true;
        else
    	return false;
    }
    
    
    public static boolean test1() throws Throwable {
    
        org.joda.time.DateTimeZone BRAZIL = org.joda.time.DateTimeZone.forID("America/Sao_Paulo");
        org.joda.time.DateTimeZone PORTUGAL = org.joda.time.DateTimeZone.forID("Europe/Lisbon");
        org.joda.time.DateTime start = new org.joda.time.DateTime(2013, 10, 13, 23, 59, BRAZIL);
        org.joda.time.DateTime end = new org.joda.time.DateTime(2013, 10, 20, 3, 0, PORTUGAL);
    
    		
        if (daysBetween(start, end) == 7)
    	return true;
        else
    	return false;
    }
    
    public static boolean test2() throws Throwable {
    
        org.joda.time.DateTimeZone SH = org.joda.time.DateTimeZone.forID("Asia/Shanghai");
        org.joda.time.DateTimeZone CT = org.joda.time.DateTimeZone.forID("America/Chicago");
    
        org.joda.time.DateTime start = new org.joda.time.DateTime(2013, 11, 13, 10, 59, SH);
        org.joda.time.DateTime end = new org.joda.time.DateTime(2013, 11, 20, 5, 0, CT);
    		
        if (daysBetween(start, end) == 7)
    	return true;
        else
    	return false;
    }
    
    public static boolean test() throws Throwable {
        return test0() && test1() && test2();
    }
     

    public static void main(String[] args) {
        int maxTime = Integer.parseInt(args[0]);
        SketchExecutor.setType(ExecutorType.JUZI);
        boolean found = false;
        boolean timeout = false;
        int i = 0;
        long begin = System.currentTimeMillis();
        long checkpoint = 0;
        do {
            if(System.currentTimeMillis() - begin >= maxTime * 1000) {
                timeout = true;
                break;
            }
            i ++;
            if(System.currentTimeMillis() - checkpoint >= 5000) {
                System.out.println(i + " executions tried!");
                checkpoint = System.currentTimeMillis();
            }
            request.reset();
            try {
                if(!test()) {
                    throw new RuntimeException("****TEST FAILURE");
                }
                found = true;
                break;
            } catch (Throwable e) {
               // e.printStackTrace();
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
    }

}
