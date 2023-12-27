package r.android.graphics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BezierPath 
{
    
    static final Matcher matchPoint = Pattern.compile("\\s*(\\d+)[^\\d]+(\\d+)\\s*").matcher("");

    BezierListProducer path;
    
    /** Creates a new instance of Animate */
    public BezierPath()
    {
    }

    public void parsePathString(String d) {

        this.path = new BezierListProducer();

        parsePathList(d);
    }
    
    protected void parsePathList(String list)
    {
        final Matcher matchPathCmd = Pattern.compile("([MmLlHhVvAaQqTtCcSsZz])|([-+]?((\\d*\\.\\d+)|(\\d+))([eE][-+]?\\d+)?)").matcher(list);

        //Tokenize
        LinkedList<String> tokens = new LinkedList<String>();
        while (matchPathCmd.find())
        {
            tokens.addLast(matchPathCmd.group());
        }

        char curCmd = 'Z';
        while (tokens.size() != 0)
        {
            String curToken = tokens.removeFirst();
            char initChar = curToken.charAt(0);
            if ((initChar >= 'A' && initChar <= 'Z') || (initChar >= 'a' && initChar <= 'z'))
            {
                curCmd = initChar;
            } else
            {
                tokens.addFirst(curToken);
            }

            switch (curCmd)
            {
                case 'M':
                    path.movetoAbs(nextFloat(tokens), nextFloat(tokens));
                    curCmd = 'L';
                    break;
                case 'm':
                	path.movetoRel(nextFloat(tokens), nextFloat(tokens));
                    curCmd = 'l';
                    break;
                case 'L':
                    path.linetoAbs(nextFloat(tokens), nextFloat(tokens));
                    break;
                case 'l':
                	path.linetoRel(nextFloat(tokens), nextFloat(tokens));
                    break;
                case 'H':
                    path.linetoHorizontalAbs(nextFloat(tokens));
                    break;
                case 'h':
                	path.linetoHorizontalRel(nextFloat(tokens));
                    break;
                case 'V':
                    path.linetoVerticalAbs(nextFloat(tokens));
                    break;
                case 'v':
                	path.linetoVerticalAbs(nextFloat(tokens));
                    break;
                case 'A':
                case 'a':
                    break;
                case 'Q':
                    path.curvetoQuadraticAbs(nextFloat(tokens), nextFloat(tokens),
                        nextFloat(tokens), nextFloat(tokens));
                    break;
                case 'q':
                	path.curvetoQuadraticAbs(nextFloat(tokens), nextFloat(tokens),
                        nextFloat(tokens), nextFloat(tokens));
                    break;
                case 'T':
                    path.curvetoQuadraticSmoothAbs(nextFloat(tokens), nextFloat(tokens));
                    break;
                case 't':
                	path.curvetoQuadraticSmoothRel(nextFloat(tokens), nextFloat(tokens));
                    break;
                case 'C':
                    path.curvetoCubicAbs(nextFloat(tokens), nextFloat(tokens),
                        nextFloat(tokens), nextFloat(tokens),
                        nextFloat(tokens), nextFloat(tokens));
                    break;
                case 'c':
                	path.curvetoCubicRel(nextFloat(tokens), nextFloat(tokens),
                        nextFloat(tokens), nextFloat(tokens),
                        nextFloat(tokens), nextFloat(tokens));
                    break;
                case 'S':
                    path.curvetoCubicSmoothAbs(nextFloat(tokens), nextFloat(tokens),
                        nextFloat(tokens), nextFloat(tokens));
                    break;
                case 's':
                	path.curvetoCubicSmoothRel(nextFloat(tokens), nextFloat(tokens),
                        nextFloat(tokens), nextFloat(tokens));
                    break;
                case 'Z':
                case 'z':
                    path.closePath();
                    break;
                default:
                    throw new RuntimeException("Invalid path element");
            }
        }
    }
    
    static protected float nextFloat(LinkedList<String> l)
    {
        String s = l.removeFirst();
        return Float.parseFloat(s);
    }
    
    /**
     * Evaluates this animation element for the passed interpolation time.  Interp
     * must be on [0..1].
     */
    public PointF eval(float interp)
    {
        PointF point = new PointF();
  
        
        double curLength = path.curveLength * interp;
        for (Iterator<Bezier> it = path.bezierSegs.iterator(); it.hasNext();)
        {
            Bezier bez = it.next();
            
            double bezLength = bez.getLength();
            if (curLength <= bezLength)
            {
                double param = curLength / bezLength;
                bez.eval(param, point);
                break;
            }
            
            curLength -= bezLength;
        }
        
        return point;
    }
    
    private static final int MAX_NUM_POINTS = 100;
    private static final int FRACTION_OFFSET = 0;
    private static final int X_OFFSET = 1;
    private static final int Y_OFFSET = 2;
    private static final int NUM_COMPONENTS = 3;
    public float[] approximate(float acceptableError) {
        if (acceptableError < 0) {
          throw new IllegalArgumentException("acceptableError must be greater than or equal to 0");
        }
        // Measure the total length the whole pathData.
        float totalLength = 0;
        // The sum of the previous contour plus the current one. Using the sum here
        // because we want to directly subtract from it later.
        final List<Float> summedContourLengths = new ArrayList<>();
        summedContourLengths.add(0f);
        
        for (Iterator<Bezier> it = path.bezierSegs.iterator(); it.hasNext();) {
            Bezier bez = it.next();
            double pathLength = bez.getLength();
            totalLength += pathLength;
            summedContourLengths.add(totalLength);
        }

        final int numPoints = Math.min(MAX_NUM_POINTS, (int) (totalLength / acceptableError) + 1);

        final float[] coords = new float[NUM_COMPONENTS * numPoints];
        PointF position = null;

        final float step = totalLength / (numPoints - 1);
        float cumulativeDistance = 0;

        for (int i = 0; i < numPoints; i++) {
        	position = eval(cumulativeDistance / totalLength);

        	coords[i * NUM_COMPONENTS + FRACTION_OFFSET] = cumulativeDistance / totalLength;
        	coords[i * NUM_COMPONENTS + X_OFFSET] = position.x;
        	coords[i * NUM_COMPONENTS + Y_OFFSET] = position.y;

        	cumulativeDistance = Math.min(cumulativeDistance + step, totalLength);
        }

        coords[(numPoints - 1) * NUM_COMPONENTS + FRACTION_OFFSET] = 1f;
        return coords;
      }

}
