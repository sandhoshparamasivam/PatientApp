package com.orane.icliniq.fileattach_library;


public abstract class DefaultCallback implements EasyImage.Callbacks {

    @Override
    public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
    }

    @Override
    public void onCanceled(EasyImage.ImageSource source, int type) {
    }
}