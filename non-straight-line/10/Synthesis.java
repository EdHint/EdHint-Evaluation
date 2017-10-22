import sketch4j.executor.ExecutorType;
import sketch4j.executor.SketchExecutor;
import sketch4j.request.invocation.BatchInvocationRequest;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

public class Synthesis {
    public static void main(String[] args) {
        BatchInvocationRequest request = com.google.javascript.jscomp.ClosureCodeRemoval.request;
        Class<?> test = com.google.javascript.jscomp.ClosureCodeRemovalTest.class;
        JUnitCore junit = new JUnitCore();
        int numOfHoles = 3;

        int maxTime = 3600;
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
            Result result = junit.run(test);
            if(result.wasSuccessful()) {
                found = true;
                break;                
            }
        } while (SketchExecutor.incrementCounter());
        if(found) {
            System.out.println("Found solution:");
            System.out.println("----------------------------------------------------------------");
            for(int j=0; j<numOfHoles; j++) {
                System.out.println("Hole " + j + ":");
                System.out.println(request.getCandidateBatchInvocation(j));
            }
            System.out.println("----------------------------------------------------------------");
        } else if(timeout) {
            System.out.println("Timeout! Max execution time: " + maxTime + " seconds.");
        } else {
            System.out.println("No solution!");
        }
        System.out.println("Number of executions: " + i);
    } 
}
