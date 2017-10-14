import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

import sketch4j.executor.ExecutorType;
import sketch4j.executor.SketchExecutor;
import sketch4j.request.invocation.BatchInvocationRequest;

public class Main {

    private static BatchInvocationRequest request = new BatchInvocationRequest();



    public static void exclusiveOr(Area area, Rectangle2D rect) {
        request.newBatchInvocation(0).addDeclaringClassesFromPrefix("java.awt.geom")
                .addArgument(java.awt.geom.Area.class, "area", area).addArgument(Rectangle2D.class, "rect", rect)
                .setReturnType(void.class).setMaxSearchDepth(3).invoke();
    }

    public static void rightSolution(Area arg0, Rectangle2D rect) {
        Area b = new Area(rect);
        arg0.exclusiveOr(b);
    }

    public static boolean test() {
        return test0() && test1();
    }
    
    public static boolean test0() {
        java.awt.geom.Area a1 = new java.awt.geom.Area(new java.awt.geom.Rectangle2D.Double(0, 0, 10, 2));
        java.awt.geom.Area a2 = new java.awt.geom.Area(a1);
        exclusiveOr(a1, new Rectangle2D.Double(-2, 1, 3, 3));
        rightSolution(a2, new Rectangle2D.Double(-2, 1, 3, 3));
        return a1.equals(a2);
    }
    
    public static boolean test1() {
        java.awt.geom.Area a1 = new java.awt.geom.Area(new java.awt.geom.Rectangle2D.Double(-3, 4, 5, 1));
        java.awt.geom.Area a2 = new java.awt.geom.Area(a1);
        exclusiveOr(a1, new Rectangle2D.Double(-3, 4, 2, 2));
        rightSolution(a2, new Rectangle2D.Double(-3, 4, 2, 2));
        return a1.equals(a2);
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
