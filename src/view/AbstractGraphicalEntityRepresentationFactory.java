package view;

import javax.swing.JPanel;

/**
 * TODO
 *
 * @param <V> the type of the View associated with this factory
 *
 * @author Alex Mandelias
 */
abstract class AbstractGraphicalEntityRepresentationFactory<V extends AbstractGUIView>
        implements AbstractEntityRepresentationFactory<JPanel> {

	/**
	 * Sets the View of this factory, if not already set.
	 *
	 * @param newView the new view of this factory
	 */
	abstract void initializeView(V newView);
}
