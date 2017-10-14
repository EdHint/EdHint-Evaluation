import sketch4j.executor.ExecutorType;
import sketch4j.executor.SketchExecutor;
import sketch4j.request.invocation.BatchInvocationRequest;

public class Main {

    private static BatchInvocationRequest request = new BatchInvocationRequest();



    public static java.lang.String getAttributeById(java.io.File arg0, java.lang.String arg1) {
        return (java.lang.String) request.newBatchInvocation(0)
                 .addDeclaringClassesFromPrefix("org.w3c.dom")
                 .addDeclaringClassesFromPrefix("javax.xml.parsers")
                 .addDeclaringClassesFromPrefix("org.xml.sax")
                 .addDeclaringClasses(java.io.File.class)
                 .addArgument(java.io.File.class, "arg0", arg0)
                 .addArgument(java.lang.String.class, "arg1", arg1)
                 .setReturnType(java.lang.String.class)
                 .setMinSearchDepth(5)
                 .setMaxSearchDepth(5)
                 .invoke();
    }


    public static boolean test() throws Throwable {
        java.io.File file = new java.io.File("res/doc.xml");
        java.lang.String id = "brand";
        
        java.lang.String n = getAttributeById(file,id);
        boolean flag = (n != null) && (n.equals("ut"));
    
        if(flag) 
            return true;
        else 
            return false;
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
