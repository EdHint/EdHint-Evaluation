import sketch4j.executor.ExecutorType;
import sketch4j.executor.SketchExecutor;
import sketch4j.request.invocation.BatchInvocationRequest;

public class Main {

    private static BatchInvocationRequest request = new BatchInvocationRequest();



    public static org.w3c.dom.DocumentType getDoctypeByString(java.lang.String arg0) {
        return (org.w3c.dom.DocumentType) request.newBatchInvocation(0)
                 .addDeclaringClassesFromPrefix("org.w3c.dom")
                 .addDeclaringClassesFromPrefix("javax.xml.parsers")
                 .addDeclaringClassesFromPrefix("org.xml.sax")
                 .addDeclaringClasses(java.io.StringReader.class)
                 .addArgument(java.lang.String.class, "arg0", arg0)
                 .setReturnType(org.w3c.dom.DocumentType.class)
                 .setMinSearchDepth(6)
                 .setMaxSearchDepth(6)
                 .invoke();
    }


    public static boolean test() throws Throwable {
        java.lang.String xmlStr = "<?xml version=\"1.0\"?><!DOCTYPE note [<!ELEMENT note (to,from,heading,body)><!ELEMENT body (#PCDATA)>]><note><heading>Reminder</heading><body>Don't forget me this weekend</body></note>";
        org.w3c.dom.DocumentType elem = getDoctypeByString(xmlStr);
        boolean flag = (elem != null) && (elem.getName().equals("note"));
    
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
