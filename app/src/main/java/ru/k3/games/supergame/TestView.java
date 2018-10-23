package ru.k3.games.supergame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestView extends SurfaceView implements SurfaceHolder.Callback {
    private DrawThread drawThread;

    public TestView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        drawThread = new DrawThread(holder);
        drawThread.setRunning(true);
        drawThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        drawThread.setRunning(false);
        while (retry) {
            try {
                drawThread.join();
                retry = false;
            } catch (InterruptedException ignored) {
            }
        }
    }

    class DrawData {
        float x1;
        float y1;
        float x2;
        float y2;

        DrawData(float x1, float y1, float x2, float y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }
    }

    class DrawThread extends Thread {
        private final SurfaceHolder surfaceHolder;
        private final Random random;
        private final List<DrawData> dataList;
        private final Paint textPaint;
        private boolean running = false;

        public DrawThread(SurfaceHolder surfaceHolder) {
            this.surfaceHolder = surfaceHolder;
            this.random = new Random();
            this.dataList = new ArrayList<>();
            this.textPaint = new Paint();
            textPaint.setTextSize(36.0f);
        }

        public void setRunning(boolean running) {
            this.running = running;
        }

        @Override
        public void run() {
            Canvas canvas;
            int i = 0;
            float x2 = textPaint.measureText("Колян пидр");
            while (running) {
                canvas = null;
                try {
                    canvas = surfaceHolder.lockCanvas();
                    if (canvas == null) {
                        continue;
                    }
                    canvas.drawColor(Color.WHITE);
                    if (i == 100) {
                        DrawData data = new DrawData(random.nextInt(600),
                                random.nextInt(600), x2, 0);
                        data.y2 = data.y1 - 36;
                        dataList.add(data);
                        i = 0;
                    }
                    for (DrawData data : dataList) {
                        canvas.drawText("Колян пидр", data.x1, data.y1, textPaint);
                    }
                    i++;
                } finally {
                    if (canvas != null) {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }
    }
}
