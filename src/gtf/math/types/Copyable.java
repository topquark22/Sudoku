package gtf.math.types;


/**
 * Declares something can be copied to an independent
 * instance of its own type.
 * 
 * @author gtf
 *
 * @param <T> the type
 */
public interface Copyable<T> {

  T copyOf();
}
