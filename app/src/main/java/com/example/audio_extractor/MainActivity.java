package com.example.audio_extractor;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.documentfile.provider.DocumentFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 123;
    private static final int PICK_VIDEO_REQUEST_CODE = 456;
    private Uri selectedVideoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Request storage permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_CODE);
        }
    }

    public void selectVideo(View view) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("video/*");
        startActivityForResult(intent, PICK_VIDEO_REQUEST_CODE);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == PICK_VIDEO_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
//            selectedVideoUri = data.getData();
//            // Perform necessary operations with the selected video URI
//        }
//    }
@Override
protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == PICK_VIDEO_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
        selectedVideoUri = data.getData();
        TextView tvSelectedVideo = findViewById(R.id.tvSelectedVideo);
        tvSelectedVideo.setText(selectedVideoUri.toString());

        // Enable the "Extract Audio" button
        Button btnExtractAudio = findViewById(R.id.btnExtractAudio);
        btnExtractAudio.setEnabled(true);
    }
}




//    public void extractAudio(View view) {
//        if (selectedVideoUri == null) {
//            Toast.makeText(this, "Please select a video file", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        MediaExtractor extractor = new MediaExtractor();
//        try {
//            InputStream inputStream = getContentResolver().openInputStream(selectedVideoUri);
//            File videoFile = createTemporaryFile(inputStream);
//            extractor.setDataSource(videoFile.getPath());
//
//            int trackCount = extractor.getTrackCount();
//            int audioTrackIndex = -1;
//            for (int i = 0; i < trackCount; i++) {
//                MediaFormat format = extractor.getTrackFormat(i);
//                String mime = format.getString(MediaFormat.KEY_MIME);
//                if (mime.startsWith("audio/")) {
//                    audioTrackIndex = i;
//                    break;
//                }
//            }
//
//            if (audioTrackIndex >= 0) {
//                extractor.selectTrack(audioTrackIndex);
//
//                String outputFolderPath = Environment.getExternalStorageDirectory()
//                        + File.separator + "ExtractedAudio"; // Specify the folder name
//                File outputFolder = new File(outputFolderPath);
//                if (!outputFolder.exists()) {
//                    outputFolder.mkdirs(); // Create the folder if it doesn't exist
//                }
//
//                String outputPath = outputFolderPath + File.separator + "extracted_audio.mp3";
//                File outputFile = new File(outputPath);
//
//                FileOutputStream fos = new FileOutputStream(outputFile);
//                ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);
//
//                while (true) {
//                    int sampleSize = extractor.readSampleData(buffer, 0);
//                    if (sampleSize < 0) {
//                        break;
//                    }
//
//                    byte[] data = new byte[sampleSize];
//                    buffer.get(data);
//
//                    fos.write(data);
//                    buffer.clear();
//                    extractor.advance();
//                }
//
//                fos.close();
//
//                Toast.makeText(this, "Audio extracted successfully", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(this, "No audio track found in the video", Toast.LENGTH_SHORT).show();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            Toast.makeText(this, "Error extracting audio", Toast.LENGTH_SHORT).show();
//        } finally {
//            extractor.release();
//        }
//    }

//    public void extractAudio(View view) {
//        if (selectedVideoUri == null) {
//            Toast.makeText(this, "Please select a video file", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        MediaExtractor extractor = new MediaExtractor();
//        try {
//            InputStream inputStream = getContentResolver().openInputStream(selectedVideoUri);
//            File videoFile = createTemporaryFile(inputStream);
//            extractor.setDataSource(videoFile.getPath());
//
//            int trackCount = extractor.getTrackCount();
//            int audioTrackIndex = -1;
//            for (int i = 0; i < trackCount; i++) {
//                MediaFormat format = extractor.getTrackFormat(i);
//                String mime = format.getString(MediaFormat.KEY_MIME);
//                if (mime.startsWith("audio/")) {
//                    audioTrackIndex = i;
//                    break;
//                }
//            }
//
//
//
//            if (audioTrackIndex >= 0) {
//                extractor.selectTrack(audioTrackIndex);
//
//                String outputFolderPath = Environment.getExternalStorageDirectory()
//                        + File.separator + "Music"; // Specify the folder name
//                File outputFolder = new File(outputFolderPath);
//                if (!outputFolder.exists()) {
//                    outputFolder.mkdirs(); // Create the folder if it doesn't exist
//                }
//
//                MediaFormat audioFormat = extractor.getTrackFormat(audioTrackIndex);
//                String audioMimeType = audioFormat.getString(MediaFormat.KEY_MIME);
//                String audioExtension = getFileExtensionFromMimeType(audioMimeType);
//
//                String outputPath = outputFolderPath + File.separator + "extracted_audio." + audioExtension;
//                File outputFile = new File(outputPath);
//
//                FileOutputStream fos = new FileOutputStream(outputFile);
//                ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);
//
//                while (true) {
//                    int sampleSize = extractor.readSampleData(buffer, 0);
//                    if (sampleSize < 0) {
//                        break;
//                    }
//
//                    byte[] data = new byte[sampleSize];
//                    buffer.get(data);
//
//                    fos.write(data);
//                    buffer.clear();
//                    extractor.advance();
//                }
//
//                fos.close();
//
//                Toast.makeText(this, "Audio extracted successfully", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(this, "No audio track found in the video", Toast.LENGTH_SHORT).show();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            Toast.makeText(this, "Error extracting audio", Toast.LENGTH_SHORT).show();
//        } finally {
//            extractor.release();
//        }
//    }

    public void extractAudio(View view) {
        if (selectedVideoUri == null) {
            Toast.makeText(this, "Please select a video file", Toast.LENGTH_SHORT).show();
            return;
        }

        MediaExtractor extractor = new MediaExtractor();
        try {
            InputStream inputStream = getContentResolver().openInputStream(selectedVideoUri);
            File videoFile = createTemporaryFile(inputStream);
            extractor.setDataSource(videoFile.getPath());

            int trackCount = extractor.getTrackCount();
            int audioTrackIndex = -1;
            for (int i = 0; i < trackCount; i++) {
                MediaFormat format = extractor.getTrackFormat(i);
                String mime = format.getString(MediaFormat.KEY_MIME);
                if (mime.startsWith("audio/")) {
                    audioTrackIndex = i;
                    break;
                }
            }

            if (audioTrackIndex >= 0) {
                extractor.selectTrack(audioTrackIndex);

                String outputFolderPath = Environment.getExternalStorageDirectory()
                        + File.separator + "Music"; // Specify the folder name
                File outputFolder = new File(outputFolderPath);
                if (!outputFolder.exists()) {
                    outputFolder.mkdirs(); // Create the folder if it doesn't exist
                }

                MediaFormat audioFormat = extractor.getTrackFormat(audioTrackIndex);
                String audioMimeType = audioFormat.getString(MediaFormat.KEY_MIME);
                String audioExtension = getFileExtensionFromMimeType(audioMimeType);

                String outputPath = outputFolderPath + File.separator + "extracted_audio." + audioExtension;
                File outputFile = new File(outputPath);

                FileOutputStream fos = new FileOutputStream(outputFile);
                ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);

                while (true) {
                    int sampleSize = extractor.readSampleData(buffer, 0);
                    if (sampleSize < 0) {
                        break;
                    }

                    byte[] data = new byte[sampleSize];
                    buffer.get(data);

                    fos.write(data);
                    buffer.clear();
                    extractor.advance();
                }

                fos.close();

                // Show toast with the file path
                Toast.makeText(this, "Audio extracted successfully. File saved at: " + outputFile.getAbsolutePath(), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "No audio track found in the video", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error extracting audio", Toast.LENGTH_SHORT).show();
        } finally {
            extractor.release();
        }
    }


    private String getFileExtensionFromMimeType(String mimeType) {
        String extension = "";

        if (mimeType != null && !mimeType.isEmpty()) {
            String[] parts = mimeType.split("/");
            if (parts.length == 2) {
                extension = parts[1];
            }
        }

        return extension;
    }


    private File createTemporaryFile(InputStream inputStream) throws IOException {
        File tempFile = File.createTempFile("temp_video", ".mp4", getCacheDir());
        FileOutputStream fos = new FileOutputStream(tempFile);
        byte[] buffer = new byte[8 * 1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            fos.write(buffer, 0, bytesRead);
        }
        fos.close();
        inputStream.close();
        return tempFile;
    }


    // Handle permission request result
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, perform necessary operations
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
