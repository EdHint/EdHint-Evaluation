import sketch4j.executor.ExecutorType;
import sketch4j.executor.SketchExecutor;
import sketch4j.request.invocation.BatchInvocationRequest;

public class Main {

    private static BatchInvocationRequest request = new BatchInvocationRequest();



    public static boolean isPointInside(java.awt.geom.Rectangle2D arg0, java.awt.geom.Point2D arg1) {
        return (Boolean) request.newBatchInvocation(0)
                 .addDeclaringClassesFromPrefix("java.awt.geom")
                 .addDeclaringClasses(java.awt.geom.Rectangle2D.class, java.awt.geom.Point2D.class)
                 .addArgument(java.awt.geom.Rectangle2D.class, "arg0", arg0)
                 .addArgument(java.awt.geom.Point2D.class, "arg1", arg1)
                 .setReturnType(boolean.class)
                 .setMaxSearchDepth(5)
                 .invoke();
    }


    public static boolean test0() throws Throwable {
        java.awt.geom.Rectangle2D rect = new java.awt.geom.Rectangle2D.Double(0,0,1,1);
        java.awt.geom.Point2D p = new java.awt.geom.Point2D.Double(0.5, 0.5); 
        return isPointInside(rect, p) == true;
    }
    
    public static boolean test1() throws Throwable {
        java.awt.geom.Rectangle2D rect = new java.awt.geom.Rectangle2D.Double(0,0,1,1);
        java.awt.geom.Point2D p = new java.awt.geom.Point2D.Double(0.5, 2.5); 
        return isPointInside(rect, p) == false;
    }
    
    public static boolean test2() throws Throwable {
        java.awt.geom.Rectangle2D rect = new java.awt.geom.Rectangle2D.Double(1,2,3,4);
        java.awt.geom.Point2D p = new java.awt.geom.Point2D.Double(2, 3); 
        return isPointInside(rect, p) == true;
    }
    
    public static boolean test3() throws Throwable {
        java.awt.geom.Rectangle2D rect = new java.awt.geom.Rectangle2D.Double(1,2,1,2);
        java.awt.geom.Point2D p = new java.awt.geom.Point2D.Double(1.1, 2.1); 
        return isPointInside(rect, p) == true;
    }
    
    public static boolean test4() throws Throwable {
        java.awt.geom.Rectangle2D rect = new java.awt.geom.Rectangle2D.Double(1,2,1,2);
        java.awt.geom.Point2D p = new java.awt.geom.Point2D.Double(1.9, 3.9); 
        return isPointInside(rect, p) == true;
    }
    
    public static boolean test5() throws Throwable {
        java.awt.geom.Rectangle2D rect = new java.awt.geom.Rectangle2D.Double(1,2,1,2);
        java.awt.geom.Point2D p = new java.awt.geom.Point2D.Double(2, 4.1); 
        return isPointInside(rect, p) == false;
    }
    
    public static boolean test6() throws Throwable {
        java.awt.geom.Rectangle2D rect = new java.awt.geom.Rectangle2D.Double(1,2,5,8);
        java.awt.geom.Point2D p = new java.awt.geom.Point2D.Double(3, 11); 
        return isPointInside(rect, p) == false;
    }
    
    public static boolean test7() throws Throwable {
        java.awt.geom.Rectangle2D rect = new java.awt.geom.Rectangle2D.Double(1,2,5,8);
        java.awt.geom.Point2D p = new java.awt.geom.Point2D.Double(3, 9.9); 
        return isPointInside(rect, p) == true;
    }
    
    
    public static boolean test() throws Throwable {
        return test0() && test1() && test2() && test3() && test4() && test5() && test6() && test7();
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
