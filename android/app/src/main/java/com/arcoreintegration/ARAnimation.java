package com.arcoreintegration;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.google.ar.core.Anchor;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.SkeletonNode;
import com.google.ar.sceneform.animation.ModelAnimator;
import com.google.ar.sceneform.rendering.AnimationData;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;

public class ARAnimation extends AppCompatActivity {

    private ArFragment arFragment;
    private ModelAnimator modelAnimator;
    int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.animation_fragment);

        arFragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {

            createModel(hitResult.createAnchor(), arFragment);


        });
    }

    private void createModel(Anchor anchor, ArFragment arFragment) {

        ModelRenderable.builder().setSource(this, Uri.parse("skull.sfb")).build().thenAccept(modelRenderable -> {
            AnchorNode anchorNode = new AnchorNode(anchor);
            SkeletonNode skeletonNode = new SkeletonNode();
            skeletonNode.setParent(anchorNode);
            skeletonNode.setRenderable(modelRenderable);

            arFragment.getArSceneView().getScene().addChild(anchorNode);

            Button button = findViewById(R.id.button);
            button.setOnClickListener(view -> animateModel(modelRenderable));
        });

    }

    private void animateModel(ModelRenderable modelRenderable) {

        if (modelAnimator != null && modelAnimator.isRunning()) {
            modelAnimator.end();

            int animationCount = modelRenderable.getAnimationDataCount();
            if(i==animationCount){
                i=0;
            }
            AnimationData animationData= modelRenderable.getAnimationData(i);

            modelAnimator=new ModelAnimator(animationData,modelRenderable);
            modelAnimator.start();
            i++;
        }
    }
}
