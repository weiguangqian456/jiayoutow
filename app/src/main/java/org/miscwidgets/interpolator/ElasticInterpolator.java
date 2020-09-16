package org.miscwidgets.interpolator;

import android.view.animation.Interpolator;

/**
 * ClassName:      ElasticInterpolator
 * <p>
 * Author:
 * <p>
 * CreateDate:      2020/9/14 11:34
 * <p>
 * Description:
 */
public class ElasticInterpolator implements Interpolator {

    private EasingType.Type type;
    private float amplitude;
    private float period;

    public ElasticInterpolator(EasingType.Type type, float amplitude, float period) {
        this.type = type;
        this.amplitude = amplitude;
        this.period = period;
    }

    public float getInterpolation(float t) {
        if (type == EasingType.Type.IN) {
            return in(t, amplitude, period);
        } else
        if (type == EasingType.Type.OUT) {
            return out(t, amplitude, period);
        } else
        if (type == EasingType.Type.INOUT) {
            return inout(t, amplitude, period);
        }
        return 0;
    }

    private float in(float t, float a, float p) {
        if (t == 0) {
            return 0;
        }
        if (t >= 1) {
            return 1;
        }
        if (p == 0) {
            p = 0.3f;
        }
        float s;
        if (a == 0 || a < 1) {
            a = 1;
            s = p / 4;
        }
        else {
            s = (float) (p/(2*Math.PI) * Math.asin(1/a));
        }
        return (float) (-(a*Math.pow(2,10*(t-=1)) * Math.sin((t-s)*(2*Math.PI)/p)));
    }

    private float out(float t, float a, float p) {
        if (t == 0) {
            return 0;
        }
        if (t >= 1) {
            return 1;
        }
        if (p == 0) {
            p = 0.3f;
        }
        float s;
        if (a == 0 || a < 1) {
            a = 1;
            s = p / 4;
        }
        else {
            s = (float) (p/(2*Math.PI) * Math.asin(1/a));
        }
        return (float) (a*Math.pow(2,-10*t) * Math.sin((t-s)*(2*Math.PI)/p) + 1);
    }

    private float inout(float t, float a, float p) {
        if (t == 0) {
            return 0;
        }
        if (t >= 1) {
            return 1;
        }
        if (p == 0) {
            p = .3f*1.5f;
        }
        float s;
        if (a == 0 || a < 1) {
            a = 1;
            s = p / 4;
        }
        else {
            s = (float) (p/(2*Math.PI) * Math.asin(1/a));
        }
        t *= 2;
        if (t < 1) {
            return (float) (-.5*(a*Math.pow(2,10*(t-=1)) * Math.sin((t-s)*(2*Math.PI)/p)));
        } else {
            return (float) (a*Math.pow(2,-10*(t-=1)) * Math.sin((t-s)*(2*Math.PI)/p)*.5 + 1);
        }
    }
}

