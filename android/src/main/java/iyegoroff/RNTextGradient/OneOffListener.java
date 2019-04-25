package iyegoroff.RNTextGradient;

class OneOffListener {
  private Runnable mRemoval;
  private Runnable mUpdate;

  OneOffListener(Runnable update) {
    mUpdate = update;
  }

  void addRemoval(Runnable removal) {
    mRemoval = removal;
  }

  void trigger() {
    if (mUpdate != null) {
      mUpdate.run();
    }

    if (mRemoval != null) {
      mRemoval.run();
    }
  }
}
