package com.arcoreintegration;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.ar.core.Anchor;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.assets.RenderableSource;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;

public class ARActivity extends AppCompatActivity {

    private ArFragment arFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arcore);

        Intent intent = this.getIntent();
        String assetUrl = intent.getStringExtra("ASSET_URL");
        Log.i("assetUrl::::", assetUrl);

        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.arFragment);
        arFragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {

            placeModel(hitResult.createAnchor(), assetUrl);


        });

    }

    private void placeModel(Anchor anchor, String assetUrl) {
        ModelRenderable.builder().setSource(this, RenderableSource.builder().setSource(this, Uri.parse(assetUrl), RenderableSource.SourceType.GLTF2).setScale(0.25f).setRecenterMode(RenderableSource.RecenterMode.ROOT).build()).setRegistryId(assetUrl).build().thenAccept(modelRenderable -> addNodeToScene(modelRenderable, anchor)).exceptionally(throwable -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(throwable.getMessage()).show();
            return null;
        });
    }

    private void addNodeToScene(ModelRenderable modelRenderable, Anchor anchor) {

        AnchorNode anchorNode = new AnchorNode(anchor);
        anchorNode.setRenderable(modelRenderable);
        arFragment.getArSceneView().getScene().addChild(anchorNode);

    }
}
