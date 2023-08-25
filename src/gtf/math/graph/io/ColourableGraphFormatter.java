package gtf.math.graph.io;

import java.io.Reader;

import gtf.math.graph.ColourableGraph;


/**
 * Formats a ColourableGraph for output.
 * 
 * This is done by providing a Reader to serve it up.
 * 
 * @author gtf
 *
 * @param <T> The type used for the node addresses
 * @param <V> The type used for the node values
 */
public interface ColourableGraphFormatter<T, V> {

  Reader getReader(ColourableGraph<T, V> graph);
}
