import sketch4j.executor.ExecutorType;
import sketch4j.executor.SketchExecutor;
import sketch4j.request.invocation.BatchInvocationRequest;

public class Main {

    private static BatchInvocationRequest request = new BatchInvocationRequest();



    public static java.awt.geom.Rectangle2D getIntersection(java.awt.geom.Rectangle2D arg0, java.awt.geom.Ellipse2D arg1) {
        return (java.awt.geom.Rectangle2D) request.newBatchInvocation(0)
                 .addDeclaringClassesFromPrefix("java.awt.geom")
                 .addDeclaringClasses(java.awt.geom.Ellipse2D.class, java.awt.geom.Rectangle2D.class)
                 .addArgument(java.awt.geom.Rectangle2D.class, "arg0", arg0)
                 .addArgument(java.awt.geom.Ellipse2D.class, "arg1", arg1)
                 .setReturnType(java.awt.geom.Rectangle2D.class)
                 .setMaxSearchDepth(3)
                 .invoke();
    }


    public static boolean test() throws Throwable {
    	java.awt.geom.Rectangle2D rec = new java.awt.geom.Rectangle2D.Double(10, 20, 10, 2);
    	java.awt.geom.Ellipse2D circ = new java.awt.geom.Ellipse2D.Double(9, 19, 2, 2);
    	java.awt.geom.Rectangle2D target = new java.awt.geom.Rectangle2D.Double(10, 20, 1, 1);
    	java.awt.geom.Rectangle2D result = getIntersection(rec, circ);
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
