import sketch4j.executor.ExecutorType;
import sketch4j.executor.SketchExecutor;
import sketch4j.request.invocation.BatchInvocationRequest;

public class Main {

    private static BatchInvocationRequest request = new BatchInvocationRequest();



    public static java.awt.geom.Rectangle2D shear(java.awt.geom.Rectangle2D arg0, double arg1, double arg2) {
        return (java.awt.geom.Rectangle2D) request.newBatchInvocation(0)
                 .addDeclaringClassesFromPrefix("java.awt.geom")
                 .addDeclaringClasses(java.awt.geom.Rectangle2D.class)
                 .addArgument(java.awt.geom.Rectangle2D.class, "arg0", arg0)
                 .addArgument(double.class, "arg1", arg1)
                 .addArgument(double.class, "arg2", arg2)
                 .setReturnType(java.awt.geom.Rectangle2D.class)
                 .setMaxSearchDepth(4)
                 .invoke();
    }


    public static boolean test() throws Throwable {
        java.awt.geom.Rectangle2D area = new java.awt.geom.Rectangle2D.Double(10, 20, 10, 10);
        java.awt.geom.Rectangle2D target = new java.awt.geom.Rectangle2D.Double(20, 24, 15, 14);
        java.awt.geom.Rectangle2D result = shear(area, 0.5, 0.4);
        return (target.equals(result));
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
