package enigma;

/**
 *
 * @author Apurv Jain
 */

public class Reflector
{
    private int[] reflectorOut;
    
    //Initializing the Reflector Object
    protected Reflector(final String reflector) {
        this.reflectorOut = new int[26];
        this.setReflector(reflector);
    }
    
    //Getting output from the Reflector
    protected int getOutputOf(final int pos) {
        return (pos + this.reflectorOut[pos]) % 26;
    }
    
    //Getting wire from the Reflector
    public int getAnOutWire(final int pos) {
        return (this.reflectorOut[pos] + pos) % 26;
    }
    
    //Setting up the values for the reflector
    public void setReflector(final String reflector) {
        for (int i = 0; i < 26; ++i) {
            final int from = (char)(65 + i);
            final int to = reflector.charAt(i);
            this.reflectorOut[i] = ((from < to) ? (to - from) : ((26 - (from - to)) % 26));
        }
    }
}
