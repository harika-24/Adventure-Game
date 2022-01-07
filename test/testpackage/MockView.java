package testpackage;

import java.awt.event.KeyListener;
import java.io.IOException;

import controller.GuiController;
import view.IStarterView;
import view.IView;

/**
 * Mock class to test the view for the inputs.
 */
public class MockView implements IView, IStarterView {

  private Appendable log;

  public MockView(Appendable log) {
    this.log = log;
  }

  @Override
  public void addClickListener(GuiController listener) {
    try {
      log.append("\nClicks are happening\n");
    } catch (IOException e) {
      // do nothing
    }
  }

  @Override
  public void refresh() {
    try {
      log.append("\nrefresh\n");
    } catch (IOException e) {
      // do nothing
    }
  }

  @Override
  public void addKeysListener(KeyListener listener) {
    try {
      log.append("\nKeys are being pressed\n");
    } catch (IOException e) {
      // do nothing
    }
  }

  @Override
  public void destroyView() {
    try {
      log.append("\nDestroying view.\n");
    } catch (IOException e) {
      // do nothing
    }
  }

  @Override
  public void makeVisible(boolean visible) {
    try {
      log.append("\nMaking the view visible.\n");
    } catch (IOException e) {
      // do nothing
    }
  }
}
