package view;

/**
 * An Interface for objects that can be executed and un-executed.
 *
 * @author Alex Mandelias
 */
@FunctionalInterface
interface Undoable {

	/** Executes the Undoable */
	void execute();

	/** Un-does the Undoable */
	default void unexecute() {}
}
