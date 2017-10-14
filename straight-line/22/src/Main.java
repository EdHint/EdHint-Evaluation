import sketch4j.executor.ExecutorType;
import sketch4j.executor.SketchExecutor;
import sketch4j.request.invocation.BatchInvocationRequest;

public class Main {

    private static BatchInvocationRequest request = new BatchInvocationRequest();



    public static int getAge(org.joda.time.DateTime arg0) {
        return (Integer) request.newBatchInvocation(0)
                 .addDeclaringClassesFromPrefix("org.joda.time")
                 .addDeclaringClasses(org.joda.time.DateTime.class)
                 .addArgument(org.joda.time.DateTime.class, "arg0", arg0)
                 .setReturnType(int.class)
                 .setMaxSearchDepth(3)
                 .invoke();
    }


    public static boolean test0() throws Throwable {		
        org.joda.time.DateTime birth = new org.joda.time.DateTime(1990, 11, 13, 2, 0);
        int age = getAge(birth);
    	return (age == 26);	
    }
    	
    
    public static boolean test1() throws Throwable {		
        org.joda.time.DateTime birth = new org.joda.time.DateTime(1980, 11, 13, 2, 0);
        int age = getAge(birth);
    	return (age == 36);	
    }
    
    public static boolean test2() throws Throwable {		
        org.joda.time.DateTimeZone SH = org.joda.time.DateTimeZone.forID("Asia/Shanghai");
        org.joda.time.DateTime birth = new org.joda.time.DateTime(1980, 11, 13, 2, 0, SH);
        int age = getAge(birth);
    	return (age == 35);	
    }
    
    public static boolean test() throws Throwable {
    		
        return test0() && test1(); 
    
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
