import sketch4j.executor.ExecutorType;
import sketch4j.executor.SketchExecutor;
import sketch4j.request.invocation.BatchInvocationRequest;

public class Main {

    private static BatchInvocationRequest request = new BatchInvocationRequest();



    public static java.lang.Object evaluateByXpath(java.io.File arg0, java.lang.String arg1, javax.xml.namespace.QName arg2) {
        return (java.lang.Object) request.newBatchInvocation(0)
                 .addDeclaringClassesFromPrefix("org.w3c.dom")
                 .addDeclaringClassesFromPrefix("javax.xml")
                 .addDeclaringClassesFromPrefix("org.xml.sax")
                 .addDeclaringClasses(javax.xml.namespace.QName.class, java.io.File.class, java.lang.String.class, java.lang.Object.class)
                 .addArgument(java.io.File.class, "arg0", arg0)
                 .addArgument(java.lang.String.class, "arg1", arg1)
                 .addArgument(javax.xml.namespace.QName.class, "arg2", arg2)
                 .setReturnType(java.lang.Object.class)
                 .setMinSearchDepth(6)
                 .setMaxSearchDepth(6)
                 .invoke();
    }


    public static boolean test() throws Throwable {
        java.io.File file = new java.io.File("res/doc.xml");
        java.lang.String query = "/html/body/div[@id='container']";
        javax.xml.namespace.QName qname = javax.xml.xpath.XPathConstants.NODE;
        java.lang.Object node = evaluateByXpath(file, query, qname);
        boolean flag = (node != null) && (((org.w3c.dom.Node)node).getNodeName().equals("div"));
    
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
