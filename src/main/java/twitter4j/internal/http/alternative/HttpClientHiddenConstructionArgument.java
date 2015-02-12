/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package twitter4j.internal.http.alternative; // NOSONAR

public class HttpClientHiddenConstructionArgument {
    private static final ThreadLocal<Boolean> USEMULE = new ThreadLocal<Boolean>();
    
    private HttpClientHiddenConstructionArgument(){
    }

    public static void setUseMule(boolean b) {
        USEMULE.set(b);
    }

    public static boolean useMule() {
        return USEMULE.get();
    }
}
