import sketch4j.executor.ExecutorType;
import sketch4j.executor.SketchExecutor;
import sketch4j.request.invocation.BatchInvocationRequest;

public class Main {

    private static BatchInvocationRequest request = new BatchInvocationRequest();



    public static java.awt.geom.Area rotate(java.awt.geom.Area arg0, java.awt.geom.Point2D arg1, double arg2) {
        return (java.awt.geom.Area) request.newBatchInvocation(0)
                 .addDeclaringClassesFromPrefix("java.awt.geom")
                 .addDeclaringClasses(java.awt.geom.Area.class, java.awt.geom.Point2D.class)
                 .addArgument(java.awt.geom.Area.class, "arg0", arg0)
                 .addArgument(java.awt.geom.Point2D.class, "arg1", arg1)
                 .addArgument(double.class, "arg2", arg2)
                 .setReturnType(java.awt.geom.Area.class)
                 .setMinSearchDepth(4)
                 .setMaxSearchDepth(4)
                 .invoke();
    }


    public static boolean test() throws Throwable {
    	return test0() && test1();
    }
    
    public static boolean test0() throws Throwable {
        java.awt.geom.Area a1 = new java.awt.geom.Area(new java.awt.geom.Rectangle2D.Double(0, 0, 10, 2));
        java.awt.geom.Area a2 = new java.awt.geom.Area(new java.awt.geom.Rectangle2D.Double(-2, 0, 2, 10));
        java.awt.geom.Point2D p = new java.awt.geom.Point2D.Double(0, 0);
        java.awt.geom.Area a3 = rotate(a1, p, Math.PI / 2);
        return a2.equals(a3);
    }
    
    public static boolean test1() throws Throwable {
        java.awt.geom.Area a1 = new java.awt.geom.Area(new java.awt.geom.Rectangle2D.Double(10, 20, 10, 2));
        java.awt.geom.Area a2 = new java.awt.geom.Area(new java.awt.geom.Rectangle2D.Double(8, 20, 2, 10));
        java.awt.geom.Point2D p = new java.awt.geom.Point2D.Double(10, 20);
        java.awt.geom.Area a3 = rotate(a1, p, Math.PI / 2);
        return a2.equals(a3);
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
