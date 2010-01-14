// Created by plusminus on 23:08:24 - 27.11.2007
package com.melling.PizzaTimer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.view.View;
import com.melling.R;


public class PizzaView extends View {
    // ===========================================================
    // Fields
    // ===========================================================

    protected final int ARC_STROKE_WIDTH = 20;

    // Set start-up-values
    protected int mySecondsPassed = 0;
    protected int mySecondsTotal = 0;

    // Our Painting-Device (Pen/Pencil/Brush/Whatever...)
    protected final Paint myArcSecondPaint = new Paint();
    protected final Paint myArcMinutePaint = new Paint();
    protected final Paint myCountDownTextPaint = new Paint();
    protected final Paint myPizzaTimeTextPaint = new Paint();

    // ===========================================================
    // Constructors
    // ===========================================================

    public PizzaView(Context context) {
        super(context);
        setBackgroundResource(R.drawable.pizza);

        // Black text for the countdown
        myCountDownTextPaint.setARGB(150, 0, 0, 0);
        myCountDownTextPaint.setTextSize(110);
        myCountDownTextPaint.setFakeBoldText(true);

        // Orange text for the IT PIZZA TIME
        myPizzaTimeTextPaint.setARGB(255, 255, 60, 10);
        myPizzaTimeTextPaint.setTextSize(110);
        myPizzaTimeTextPaint.setFakeBoldText(true);

        // Our minute-arc-paint fill be a look through-red.
        myArcMinutePaint.setARGB(150, 170, 0, 0);
        myArcMinutePaint.setAntiAlias(true);
        myArcMinutePaint.setStyle(Style.STROKE);
        myArcMinutePaint.setStrokeWidth(ARC_STROKE_WIDTH);

        // Our minute-arc-paint fill be a less look through-orange.
        myArcSecondPaint.setARGB(200, 255, 130, 20);
        myArcSecondPaint.setAntiAlias(true);
        myArcSecondPaint.setStyle(Style.STROKE);
        myArcSecondPaint.setStrokeWidth(ARC_STROKE_WIDTH / 3);
    }

    // ===========================================================
    // onXYZ(...) - Methods
    // ===========================================================

    @Override
    protected void onDraw(Canvas canvas) {
        /* Calculate the time left,
  * until our pizza is finished. */
        int secondsLeft = mySecondsTotal - mySecondsPassed;

        // Check if pizza is already done
        if (secondsLeft <= 0) {
            /* Draw the "! PIZZA !"-String *  to the middle of the screen */

            String itIsPizzaTime = getResources().getString(R.string.pizza_countdown_end);

            canvas.drawText(itIsPizzaTime,
                    10, (getHeight() / 2) + 30,
                    myPizzaTimeTextPaint);
        } else {
            // At least one second left
            float angleAmountMinutes = ((mySecondsPassed * 1.0f)
                    / mySecondsTotal)
                    * 360;
            float angleAmountSeconds = ((60 - secondsLeft % 60) * 1.0f)
                    / 60
                    * 360;

            /* Calculate an Rectangle, * with some spacing to the edges */

            RectF arcRect = new RectF(ARC_STROKE_WIDTH / 2,
                    ARC_STROKE_WIDTH / 2,
                    getWidth() - ARC_STROKE_WIDTH / 2,
                    getHeight() - ARC_STROKE_WIDTH / 2);

            // Draw the Minutes-Arc  into that rectangle
            //canvas.drawArc(arcRect, -90, angleAmountMinutes, myArcMinutePaint);
            canvas.drawArc(arcRect, -90, angleAmountMinutes, false, myArcMinutePaint);

            // Draw the Seconds-Arc  into that rectangle
            canvas.drawArc(arcRect, -90, angleAmountSeconds, false, myArcSecondPaint);

            String timeDisplayString;
            if (secondsLeft > 60) // Show minutes
                timeDisplayString = "" + (secondsLeft / 60);
            else // Show seconds when less than a minute
                timeDisplayString = "" + secondsLeft;

            // Draw the remaining time.
            canvas.drawText(timeDisplayString,
                    getWidth() / 2 - (30 * timeDisplayString.length()),
                    getHeight() / 2 + 30,
                    myCountDownTextPaint);
        }
    }
    // ===========================================================
    // Getter & Setter
    // ===========================================================

    public void updateSecondsPassed(int someSeconds) {
        mySecondsPassed = someSeconds;
    }

    public void updateSecondsTotal(int totalSeconds) {
        mySecondsTotal = totalSeconds;
    }
}