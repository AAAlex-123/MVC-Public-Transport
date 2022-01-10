package view;

import javax.swing.JPanel;

/**
 * An abstract factory used to create graphical represantations of Entities.
 * This type of factory needs an {@link AbstractGUIView} to notify in case of user events 
 * (such as mouse clicks).
 *
 * @param <V> the type of the AbstractGUIView associated with this factory
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
