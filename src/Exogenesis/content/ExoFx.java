package Exogenesis.content;
import Exogenesis.graphics.ExoPal;
import Exogenesis.util.func.DrawFunc;
import Exogenesis.util.util.GraphicUtils;
import Exogenesis.util.util.UtilsTwo;
import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.util.*;
import blackhole.entities.effect.SwirlEffect;
import mindustry.entities.*;
import mindustry.entities.effect.MultiEffect;
import mindustry.graphics.*;

import static arc.graphics.g2d.Draw.rect;
import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.*;
import static arc.math.Angles.*;
import static arc.math.Angles.randLenVectors;
import static arc.math.Interp.*;
import static mindustry.Vars.state;


public class ExoFx{
    public static final Rand rand = new Rand();
    public static final Vec2 v = new Vec2();

    public static final Effect
        empyreanExplosion = new Effect(30f, 160f, e -> {
        color(e.color);
        stroke(e.fout() * 5f);
        float circleRad = 6f + e.finpow() * 60f;
        Lines.circle(e.x, e.y, circleRad);
        stroke(e.fout());
        randLenVectors(e.id + 1, 8, 5f + 60f * e.finpow(), (x, y) -> lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), 3f + e.fout() * 3f));
        rand.setSeed(e.id);
        for(int i = 0; i < 16; i++){
            float angle = rand.random(360f);
            float lenRand = rand.random(0.5f, 1f);
            Tmp.v1.trns(angle, circleRad);

            for(int s : Mathf.signs){
                Drawf.tri(e.x + Tmp.v1.x, e.y + Tmp.v1.y, e.foutpow() * 30f, e.fout() * 20f * lenRand + 6f, angle + 90f + s * 90f);
            }
        }
        color(e.color);
        for(int i = 0; i < 7; i++){
            Drawf.tri(e.x, e.y, 6f, 70f * e.fout(), i*90);
        }
        color();
        for(int i = 0; i < 7; i++){
            Drawf.tri(e.x, e.y, 3f, 35f * e.fout(), i*90);
        }
    }),
            shootGiant = new Effect(20, e -> {
                color(Pal.lightOrange, Color.gray, e.fin());
                float w = 1.2f + 12 * e.fout();
                Drawf.tri(e.x, e.y, w, 29f * e.fout(), e.rotation);
                Drawf.tri(e.x, e.y, w, 5f * e.fout(), e.rotation + 180f);
            }),
            casingLarge = new Effect(45f, e -> {
                color(Pal.lightOrange, Pal.lightishGray, Pal.lightishGray, e.fin());
                alpha(e.fout(0.5f));
                float rot = Math.abs(e.rotation) + 90f;
                int i = -Mathf.sign(e.rotation);
                float len = (4f + e.finpow() * 9f) * i;
                float lr = rot + Mathf.randomSeedRange(e.id + i + 6, 20f * e.fin()) * i;

                rect(Core.atlas.find("casing"),
                        e.x + trnsx(lr, len) + Mathf.randomSeedRange(e.id + i + 7, 3f * e.fin()),
                        e.y + trnsy(lr, len) + Mathf.randomSeedRange(e.id + i + 8, 3f * e.fin()),
                        4.5f, 8f,
                        rot + e.fin() * 50f * i
                );
            }).layer(Layer.bullet),
            PrometheusSmoke = new Effect(300f, 300f, b -> {
                float intensity = 4f;

                color(b.color, 0.7f);
                for(int i = 0; i < 4; i++){
                    rand.setSeed(b.id*2 + i);
                    float lenScl = rand.random(0.5f, 1f);
                    int fi = i;
                    b.scaled(b.lifetime * lenScl, e -> {
                        randLenVectors(e.id + fi - 1, e.fin(Interp.pow10Out), (int)(2.9f * intensity), 25f * intensity, (x, y, in, out) -> {
                            float fout = e.fout(Interp.pow5Out) * rand.random(0.5f, 1f);
                            float rad = fout * ((2f + intensity) * 2.35f);

                            Fill.circle(e.x + x, e.y + y, rad);
                            Drawf.light(e.x + x, e.y + y, rad * 3.5f, b.color, 0.5f);
                        });
                    });
                }
            }),
            PrometheusExplosionSplash = new Effect(40f, 160f, e -> {
                color(e.color);
                stroke(e.fout() * 5f);
                float circleRad = 12f + e.finpow() * 60f;
                Lines.circle(e.x, e.y, circleRad);
                stroke(e.fout());
                randLenVectors(e.id + 1, 8, 6f + 60f * e.finpow(), (x, y) -> lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), 3f + e.fout() * 3f));
                rand.setSeed(e.id);
                for(int i = 0; i < 16; i++){
                    float angle = rand.random(360f);
                    float lenRand = rand.random(0.5f, 1f);
                    Tmp.v1.trns(angle, circleRad);

                    for(int s : Mathf.signs){
                        Drawf.tri(e.x + Tmp.v1.x, e.y + Tmp.v1.y, e.foutpow() * 30f, e.fout() * 20f * lenRand + 6f, angle + 90f + s * 90f);
                    }
                }
            }),
    PrometheusShoot = new Effect(80f, e -> {
        color(Color.valueOf("feb380"));

        for(int i : Mathf.signs){
            Drawf.tri(e.x, e.y, 13f * e.fout(), 145f, e.rotation + -30f * i);
            Drawf.tri(e.x, e.y, 19f * e.fout(), 175f, e.rotation + 90f * i);
            Drawf.tri(e.x, e.y, 16f * e.fout(), 80f, e.rotation + 20f * i);
        }

        Drawf.light(e.x, e.y, 180f, Color.valueOf("feb380"), 0.9f * e.fout());
    }).followParent(true).rotWithParent(true),
            ShockWaveTrail = new Effect(25f, 600f, e -> {
                //GraphicUtils.drawShockWave(e.x, e.y, 75f, 0f, -e.rotation - 90f, 200f, 4f, 12);
                color(Color.white);
                alpha(0.666f * e.fout());

                float size = e.data instanceof Float ? (float)e.data : 200f;
                float nsize = size - 2f;
                GraphicUtils.drawShockWave(e.x, e.y, -25f, 8f, -e.rotation - 90f, nsize * e.finpow() + 2, 3f * e.finpow() + 4f, 16, 1f);
            }).layer((Layer.bullet + Layer.effect) / 2),
            PrometheusBeamShockWave = new Effect(45f, 600f, e -> {
                Draw.z(Layer.effect);
                Draw.color(ExoPal.prometheusColor,e.fout());
                Lines.stroke(6f*e.fout());               Tmp.v1.trns(e.rotation, e.fin()*30f);
                Lines.ellipse(Tmp.v1.x + e.x, Tmp.v1.y + e.y , 3f * e.fin()+0.1f,6,10, e.rotation);
                Tmp.v1.trns(e.rotation, e.fin()*20f);
                Lines.ellipse(Tmp.v1.x + e.x, Tmp.v1.y + e.y , 5f * e.fin()+0.1f,8,12, e.rotation);
                Tmp.v2.trns(e.rotation, e.fin()*10f);
                Lines.ellipse(Tmp.v2.x + e.x, Tmp.v2.y + e.y , 7f*e.fin()+0.1f,10, 18,  e.rotation);
            }),

            ullrChargeEffect = new Effect(40f, e -> {
                Angles.randLenVectors(e.id, 2, 10f, 90f, (x, y) -> {
                    float angle = Mathf.angle(x, y);
                    color(e.color, e.color, e.fin());
                    Lines.stroke(1.5f);
                    Lines.lineAngleCenter(e.x + (x * e.fout()), e.y + (y * e.fout()), angle, e.fslope() * 13f);
                });
            }),

    ullrChargeBegin = new Effect(288f, e -> {
        Color[] colors = {Pal.heal, Pal.heal, Color.white};
        for(int ii = 0; ii < 3; ii++){
            float s = (3 - ii) / 3f;
            float width = Mathf.clamp(e.time / 60f) * (20f + Mathf.absin(Time.time + (ii * 1.4f), 1.1f, 7f)) * s;
            float length = e.fin() * (80f + Mathf.absin(Time.time + (ii * 1.4f), 1.1f, 11f)) * s;
            color(colors[ii]);
            for(int i : Mathf.signs){
                float rotation = e.rotation + (i * 90f);
                Drawf.tri(e.x, e.y, width, length * 0.5f, rotation);
            }
            Drawf.tri(e.x, e.y, width, length * 1.25f, e.rotation);
        }
    }),
            ullarTipHit = new Effect(27f, e ->
                    Angles.randLenVectors(e.id, 8, 90f * e.fin(), e.rotation, 80f, (x, y) -> {
                        float angle = Mathf.angle(x, y);
                        color(e.color, e.fin());
                        Lines.stroke(1.5f);
                        Lines.lineAngleCenter(e.x + x, e.y + y, angle, e.fslope() * 13f);
                    })),
            hitMeltColor = new Effect(12, e -> {
                color(e.color);
                stroke(e.fout() * 2f);

                randLenVectors(e.id, 6, e.finpow() * 18f, (x, y) -> {
                    float ang = Mathf.angle(x, y);
                    lineAngle(e.x + x, e.y + y, ang, e.fout() * 4 + 1f);
                });
            }),
            auricCharge = new Effect(85, e -> {
                color(Color.valueOf(String.valueOf(ExoPal.empyrean)));
                Fill.circle(e.x, e.y, e.fin() * 20f);
                color(Color.white);
                Fill.circle(e.x, e.y, e.fin() * 17f);
            }),
    colorBomb = new Effect(40f, 100f, e -> {
        color(e.color);
        stroke(e.fout() * 2f);
        float circleRad = 4f + e.finpow() * 45f;
        Lines.circle(e.x, e.y, circleRad);

        color(e.color);
        for(int i = 0; i < 4; i++){
            Drawf.tri(e.x, e.y, 4f, 50f * e.fout(), i*90);
        }

        color();
        for(int i = 0; i < 4; i++){
            Drawf.tri(e.x, e.y, 2f, 15f * e.fout(), i*90);
        }

        Drawf.light(e.x, e.y, circleRad * 1.6f, e.color, e.fout());
    }),
            empyreanCharge = new Effect(100f, 100f, e -> {
                color(ExoPal.empyrean);
                stroke(e.fin() * 5f);
                float circleRad = 6f + e.finpow() * -65f;
                Lines.circle(e.x, e.y, circleRad);

                color(ExoPal.empyrean);
                for(int i = 0; i < 5; i++){
                    Drawf.tri(e.x, e.y, 5f, 60f * e.fin(), i*90);
                }
                color();
                for(int i = 0; i < 5; i++){
                    Drawf.tri(e.x, e.y, 3f, 35f * e.fin(), i*90);
                }
                Drawf.light(e.x, e.y, circleRad * 1.6f, ExoPal.empyrean, e.fin());
            }),
            coloredHitLarge = new Effect(21f, e -> {
                color(Color.white, e.color, e.fin());
                e.scaled(8f, s -> {
                    stroke(0.5f + s.fout());
                    circle(e.x, e.y, s.fin() * 11f);
                });

                stroke(0.5f + e.fout());
                randLenVectors(e.id, 6, e.fin() * 35f, e.rotation + 180f, 95f, (x, y) -> lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), e.fout() * 10f + 1f));
            }),
            lightErnExplosion = new Effect(40, e -> {
                color(ExoPal.empyrean);
                e.scaled(20, i -> {
                    stroke(3f * i.fout());
                    circle(e.x, e.y, 3f + i.fin() * 100f);
                });

                stroke(e.fout());
                randLenVectors(e.id + 1, 8, 1f + 60f * e.finpow(), (x, y) -> lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), 1f + e.fout() * 3f));

                color(Color.white);

                randLenVectors(e.id, 5, 2f + 70 * e.finpow(), (x, y) -> Fill.circle(e.x + x, e.y + y, e.fout() * 4f + 0.5f));

                Drawf.light(e.x, e.y, e.fout(pow2Out) * 100f, ExoPal.empyrean, 0.7f);
            }),
            colorBombSmaller = new Effect(30f, 100f, e -> {
                color(e.color);
                stroke(e.fout());
                float circleRad = 3f + e.finpow() * 18f;
                Lines.circle(e.x, e.y, circleRad);

                color(e.color);
                for(int i = 0; i < 4; i++){
                    Drawf.tri(e.x, e.y, 2f, 20f * e.fout(), i*90);
                }

                color();
                for(int i = 0; i < 4; i++){
                    Drawf.tri(e.x, e.y, 0.9f, 6f * e.fout(), i*90);
                }

                Drawf.light(e.x, e.y, circleRad * 1.6f, e.color, e.fout());
            }),
            lightEnrCircleSplash = new Effect(26f, e -> {
                color(e.color);
                randLenVectors(e.id, 4, 3 + 23 * e.fin(), (x, y) -> {
                    Fill.circle(e.x + x, e.y + y, e.fout() * 4.5f);
                    Drawf.light(e.x + x, e.y + y, e.fout() * 5f, e.color, 0.7f);
                });
            }),
            colorSparkShoot = new Effect(12f, e -> {
                color(Color.white, e.color, e.fin());
                stroke(e.fout() * 1.2f + 0.5f);

                randLenVectors(e.id, 7, 25f * e.finpow(), e.rotation, 50f, (x, y) -> {
                    lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), e.fin() * 5f + 2f);
                });
            });

    public static final float lightningAlign = 0.5f;

    public static Effect
            trailFadeFast =  new Effect(600f, e -> {
        if(!(e.data instanceof Trail)) return;
        Trail trail = e.data();
        //lifetime is how many frames it takes to fade out the trail
        e.lifetime = trail.length * 1.4f;

        if(!state.isPaused()){
            trail.shorten();
            trail.shorten();
        }
        trail.drawCap(e.color, e.rotation * e.foutpow());
        trail.draw(e.color, e.rotation * e.foutpow());
    }),
            explodeyscathe = new Effect(35f, 160f, e -> {
                color(e.color);
                stroke(e.fout() * 5f);
                float circleRad = 12f + e.finpow() * 60f;
                Lines.circle(e.x, e.y, circleRad);

                rand.setSeed(e.id);
                for(int i = 0; i < 16; i++){
                    float angle = rand.random(360f);
                    float lenRand = rand.random(0.5f, 1f);
                    Tmp.v1.trns(angle, circleRad);

                    for(int s : Mathf.signs){
                        Drawf.tri(e.x + Tmp.v1.x, e.y + Tmp.v1.y, e.foutpow() * 25f, e.fout() * 30f * lenRand + 6f, angle + 90f + s * 90f);
                    }
                }
                color(e.color);
                stroke(e.fout() * 7f);
                circleRad = 9f + e.finpow() * 60f;
                Lines.circle(e.x, e.y, circleRad);

                rand.setSeed(e.id);
                for(int i = 0; i < 16; i++){
                    float angle = rand.random(360f);
                    float lenRand = rand.random(0.5f, 1f);
                    Tmp.v1.trns(angle, circleRad);

                    for(int s : Mathf.signs){
                        Drawf.tri(e.x + Tmp.v1.x, e.y + Tmp.v1.y, e.foutpow() * 15f, e.fout() * 40f * lenRand + 6f, angle + 90f + s * 90f);
                    }
                }
            }),
            casing5 = new Effect(45f, e -> {
                color(Pal.lightOrange, Pal.lightishGray, Pal.lightishGray, e.fin());
                alpha(e.fout(0.5f));
                float rot = Math.abs(e.rotation) + 90f;
                int i = -Mathf.sign(e.rotation);
                float len = (4f + e.finpow() * 9f) * i;
                float lr = rot + Mathf.randomSeedRange(e.id + i + 6, 20f * e.fin()) * i;

                rect(Core.atlas.find("casing"),
                        e.x + trnsx(lr, len) + Mathf.randomSeedRange(e.id + i + 7, 3f * e.fin()),
                        e.y + trnsy(lr, len) + Mathf.randomSeedRange(e.id + i + 8, 3f * e.fin()),
                        7f, 10f,
                        rot + e.fin() * 50f * i
                );
            }).layer(Layer.bullet),
            ColorRailTrail = new Effect(16f, e -> {
                color(e.color);
                for(int i : Mathf.signs){
                    Drawf.tri(e.x, e.y, 16f * e.fout(), 21f, e.rotation + 90 + 90f * i);
                }
                Drawf.light(e.x, e.y, 60f * e.fout(), e.color, 0.5f);
            }),
            ColorRailHit = new Effect(22f, 200f, e -> {
                color(e.color);
                for(int i : Mathf.signs){
                    Drawf.tri(e.x, e.y, 14f * e.fout(), 70f, e.rotation + 140f * i);
                    Drawf.tri(e.x, e.y, 8f * e.fout(), 90f, e.rotation + 170f * i);
                }
            }),
            ColorRailShoot = new Effect(24f, e -> {
                e.scaled(10f, b -> {
                    color(e.color, e.color, b.fin());
                    stroke(b.fout() * 3f + 0.2f);
                    Lines.circle(b.x, b.y, b.fin() * 50f);
                });
                color(e.color);
                for(int i : Mathf.signs){
                    Drawf.tri(e.x, e.y, 20f * e.fout(), 145f, e.rotation + 90f * i);
                    Drawf.tri(e.x, e.y, 10f * e.fout(), 80f, e.rotation + 110f * i);
                    Drawf.tri(e.x, e.y, 5f * e.fout(), 60f, e.rotation + 160f * i);
                }
            }),

    chainLightningFade = new Effect(45f, 500f, e -> {
        if(!(e.data instanceof Position)) return;
        Position p = e.data();
        float tx = p.getX(), ty = p.getY(), dst = Mathf.dst(e.x, e.y, tx, ty);
        Tmp.v1.set(p).sub(e.x, e.y).nor();

        float normx = Tmp.v1.x, normy = Tmp.v1.y;
        float range = e.rotation;
        int links = Mathf.ceil(dst / range);
        float spacing = dst / links;

        Lines.stroke(2.5f * Mathf.curve(e.fout(), 0, 0.7f));
        Draw.color(Color.white, e.color, e.fin());

        Lines.beginLine();

        Fill.circle(e.x, e.y, Lines.getStroke() / 2);
        Lines.linePoint(e.x, e.y);

        rand.setSeed(e.id);

        float fin = Mathf.curve(e.fin(), 0, lightningAlign);
        float i;
        for(i = 0; i < links * fin; i++){
            float nx, ny;
            if(i == links - 1){
                nx = tx;
                ny = ty;
            }else{
                float len = (i + 1) * spacing;
                Tmp.v1.setToRandomDirection(rand).scl(range/2f);
                nx = e.x + normx * len + Tmp.v1.x;
                ny = e.y + normy * len + Tmp.v1.y;
            }

            Lines.linePoint(nx, ny);
        }
        Lines.endLine();
    }).followParent(false),
            circleOut  = new Effect(60f, 500f, e -> {
                Lines.stroke(2.5f * e.fout(), e.color);
                Lines.circle(e.x, e.y, e.rotation * e.fin(pow3Out));
            }),

            hitSparkHuge = new Effect(70, e -> {
                color(e.color, Color.white, e.fout() * 0.3f);
                stroke(e.fout() * 1.6f);

                rand.setSeed(e.id);
                randLenVectors(e.id, 26, e.finpow() * 65f, (x, y) -> {
                    float ang = Mathf.angle(x, y);
                    lineAngle(e.x + x, e.y + y, ang, e.fout() * rand.random(6, 9) + 3f);
                });
            }),
            chainLightningFadeReversed = new Effect(45f, 500f, e -> {
        if(!(e.data instanceof Position))return;
        Position p = e.data();
        float tx = e.x, ty = e.y, dst = Mathf.dst(p.getX(), p.getY(), tx, ty);
        Tmp.v1.set(e.x, e.y).sub(p).nor();

        float normx = Tmp.v1.x, normy = Tmp.v1.y;
        float range = e.rotation;
        int links = Mathf.ceil(dst / range);
        float spacing = dst / links;

        Lines.stroke(2.5f * Mathf.curve(e.fout(), 0, 0.7f));
        Draw.color(Color.white, e.color, e.fin());

        Lines.beginLine();

        Fill.circle(p.getX(), p.getY(), Lines.getStroke() / 2);
        Lines.linePoint(p);

        rand.setSeed(e.id);

        float fin = Mathf.curve(e.fin(), 0, lightningAlign);
        float i;
        for(i = 0; i < links *fin; i++){
            float nx, ny;
            if(i == links - 1){
                nx = tx;
                ny = ty;
            }else{
                float len = (i + 1) * spacing;
                Tmp.v1.setToRandomDirection(rand).scl(range / 2f);
                nx = p.getX() + normx * len + Tmp.v1.x;
                ny = p.getY() + normy * len + Tmp.v1.y;
            }

            Lines.linePoint(nx, ny);
        }
        Lines.endLine();
    }).followParent(false),
            spawnWave = new Effect(60f, e -> {
                stroke(3 * e.fout(), e.color);
                circle(e.x, e.y, e.rotation * e.finpow());
            }),
            hugeSmokeGray = new Effect(40f, e -> {
                Draw.color(Color.gray, Color.darkGray, e.fin());
                Angles.randLenVectors(e.id, 6, 2.0F + 19.0F * e.finpow(), (x, y) -> Fill.circle(e.x + x / 2.0F, e.y + y / 2.0F, e.fout() * 2f));
                e.scaled(25f, i -> Angles.randLenVectors(e.id, 6, 2.0F + 19.0F * i.finpow(), (x, y) -> Fill.circle(e.x + x, e.y + y, i.fout() * 4.0F)));
            }),
            square45_6_45 = new Effect(25f, e-> {
                color(Color.white, e.color, e.fin());
                stroke(e.fout() * 1.2f + 0.5f);

                randLenVectors(e.id, 8, 45f * e.finpow(), e.rotation, 50f, (x, y) -> {
                    lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), e.fin() * 5f + 2f);
                });
            }),
            supernovaCharge = new SwirlEffect(30f, 8, 2f, 30f, 90f, false, false).layer(Layer.effect - 0.03f),
            starChargeWhite = new Effect(100f, 100f, e -> {
                    color(ExoPal.starWhite);
                    Fill.circle(e.x, e.y, e.fin() * 10);
                    color(Color.white);
                    Fill.circle(e.x, e.y, e.fin() * 6);
                }).followParent(true).rotWithParent(true),
            starChargeRed = new Effect(100f, 100f, e -> {
                color(ExoPal.cronusRed);
                Fill.circle(e.x, e.y, e.fin() * 10);
                color(Color.white);
                Fill.circle(e.x, e.y, e.fin() * 6);
            }).followParent(true).rotWithParent(true),
            starChargeBlue = new Effect(100f, 100f, e -> {
                color(ExoPal.genesis);
                Fill.circle(e.x, e.y, e.fin() * 10);
                color(Color.white);
                Fill.circle(e.x, e.y, e.fin() * 6);
            }).followParent(true).rotWithParent(true),
            starChargeGreen = new Effect(100f, 100f, e -> {
                color(ExoPal.radGreen);
                Fill.circle(e.x, e.y, e.fin() * 10);
                color(Color.white);
                Fill.circle(e.x, e.y, e.fin() * 6);
            }).followParent(true).rotWithParent(true),
            starChargeDeepBlue = new Effect(100f, 100f, e -> {
                color(ExoPal.starBlue);
                Fill.circle(e.x, e.y, e.fin() * 10);
                color(Color.white);
                Fill.circle(e.x, e.y, e.fin() * 6);
            }).followParent(true).rotWithParent(true),
            starChargeYellow = new Effect(100f, 100f, e -> {
                color(ExoPal.starYellow);
                Fill.circle(e.x, e.y, e.fin() * 10);
                color(Color.white);
                Fill.circle(e.x, e.y, e.fin() * 6);
            }).followParent(true).rotWithParent(true),
            singleSpark = new Effect(21f, e -> {
                color(Color.white, e.color, e.fin());
                stroke(e.fout() * 1.1f + 0.5f);

                randLenVectors(e.id, 2, 27f * e.fin(), e.rotation, 9f, (x, y) -> {
                    lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), e.fslope() * 5f + 0.5f);
                });
            }),
            singularityDespawn = new Effect(80f, e -> {
                float rad = 24f;
                e.scaled(60f, s -> {
                    Lines.stroke(6f * s.fout(), ExoPal.genesis);
                    Lines.circle(e.x, e.y, 1.5f * rad * s.fin(pow3Out));
                });
                Lines.stroke(2f * e.fout(), Color.black);
                Lines.circle(e.x, e.y, rad * e.fin(pow3Out));
                color(ExoPal.genesis);
                for(int i = 0; i < 4; i++){
                    Drawf.tri(e.x, e.y, 3f, 50f * e.fout(), i*90);
                }

                color();
                for(int i = 0; i < 4; i++){
                    Drawf.tri(e.x, e.y, 1.3f, 30f * e.fout(), i*90);
                }
            }).layer(Layer.effect + 0.03f),
            supernovaShoot = new Effect(50f, 100f, e -> {
                e.scaled(7f, b -> {
                    color(ExoPal.genesis, ExoPal.genesisDark, b.fout());
                    Fill.circle(e.x, e.y, 20);
                });

                color(ExoPal.genesis);
                stroke(e.fout() * 3f);
                Lines.circle(e.x, e.y, 20);

                int points = 4;
                float offset = Mathf.randomSeed(e.id, 360f);
                for (int i = 0; i < points; i++) {
                    float angle = i * 360f / points + offset;
                    //for(int s : Mathf.zeroOne){
                    // Drawf.tri(e.x + Angles.trnsx(angle, rad), e.y + Angles.trnsy(angle, rad), 6f, 50f * e.fout(), angle/* + s*180f*/);
                    // }
                    // }

                    Fill.circle(e.x, e.y, 12f * e.fout());
                    color();
                    Fill.circle(e.x, e.y, 6f * e.fout());
                    Drawf.light(e.x, e.y, 20 * 1.6f, ExoPal.genesis, e.fout());
                }
            }),
            supernovaSpark = new Effect(16f, e -> {
                color(Color.white, e.color, e.fin());
                stroke(e.fout() * 1.1f + 0.5f);

                randLenVectors(e.id, 1, 40f * e.fin(), e.rotation, 0f, (x, y) -> {
                    lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), e.fslope() * 5f + 0.5f);
                });
            }),
    //test
            supernovaChargeStar = new Effect(30f, e -> {
                if(e.data instanceof Float data){
                    float r = data;

                    color(e.color);
                    alpha(e.fin() * 2f * r);
                    circle(e.x, e.y, 150f * pow2Out.apply(e.fout()) * Mathf.lerp(0.1f, 1f, r));
                }
            }),
            supernovaStarDecay = new Effect(56f, e -> randLenVectors(e.id, 1, 36f * e.finpow(), (x, y) -> {
                color(e.color);
                Fill.rect(e.x + x, e.y + y, 2.2f * e.fout(), 2.2f * e.fout(), 45f);
            })),

            starExplodeTest = new Effect(150F, 1600f, e -> {
        float rad = 60f;
        rand.setSeed(e.id);

        Draw.color(Color.white, e.color, e.fin() + 0.6f);
        float circleRad = e.fin(Interp.circleOut) * rad * 4f;
        Lines.stroke(7 * e.fout());
        Lines.circle(e.x, e.y, circleRad);
        for(int i = 0; i < 24; i++){
            Tmp.v1.set(1, 0).setToRandomDirection(rand).scl(circleRad);
            DrawFunc.tri(e.x + Tmp.v1.x, e.y + Tmp.v1.y, rand.random(circleRad / 16, circleRad / 12) * e.fout(), rand.random(circleRad / 4, circleRad / 1.5f) * (1 + e.fin()) / 2, Tmp.v1.angle() - 180);
        }
            Draw.blend(Blending.additive);
            Draw.z(Layer.effect + 0.1f);

            Fill.light(e.x, e.y, circleVertices(circleRad), circleRad, Color.clear, Tmp.c1.set(Draw.getColor()).a(e.fout(Interp.pow10Out)));
            Draw.blend();
            Draw.z(Layer.effect);

        Drawf.light(e.x, e.y, rad * e.fout(Interp.circleOut) * 4f, e.color, 0.7f);
    }).layer(Layer.effect + 0.001f),
            starExplodeBlue = new Effect(170F, 1600f, e -> {
                float rad = 85f;
                rand.setSeed(e.id);

                Draw.color(Color.white, e.color, e.fin() + 0.6f);
                float circleRad = e.fin(Interp.circleOut) * rad * 4f;
                Lines.stroke(7 * e.fout());
                Lines.circle(e.x, e.y, circleRad);
                for(int i = 0; i < 24; i++){
                    Tmp.v1.set(1, 0).setToRandomDirection(rand).scl(circleRad);
                    DrawFunc.tri(e.x + Tmp.v1.x, e.y + Tmp.v1.y, rand.random(circleRad / 16, circleRad / 12) * e.fout(), rand.random(circleRad / 4, circleRad / 1.5f) * (1 + e.fin()) / 2, Tmp.v1.angle() - 180);
                }
                Draw.blend(Blending.additive);
                Draw.z(Layer.effect + 0.1f);

                Fill.light(e.x, e.y, circleVertices(circleRad), circleRad, Color.clear, Tmp.c1.set(Draw.getColor()).a(e.fout(Interp.pow10Out)));
                Draw.blend();
                Draw.z(Layer.effect);

                Drawf.light(e.x, e.y, rad * e.fout(Interp.circleOut) * 4f, e.color, 0.7f);
            }).layer(Layer.effect + 0.001f),
            starExplodeRed = new Effect(150F, 1600f, e -> {
                float rad = 45f;
                rand.setSeed(e.id);

                Draw.color(Color.white, e.color, e.fin() + 0.6f);
                float circleRad = e.fin(Interp.circleOut) * rad * 4f;
                Lines.stroke(4 * e.fout());
                Lines.circle(e.x, e.y, circleRad);
                for(int i = 0; i < 24; i++){
                    Tmp.v1.set(1, 0).setToRandomDirection(rand).scl(circleRad);
                    DrawFunc.tri(e.x + Tmp.v1.x, e.y + Tmp.v1.y, rand.random(circleRad / 16, circleRad / 12) * e.fout(), rand.random(circleRad / 4, circleRad / 1.5f) * (1 + e.fin()) / 2, Tmp.v1.angle() - 180);
                }
                Draw.blend(Blending.additive);
                Draw.z(Layer.effect + 0.1f);

                Fill.light(e.x, e.y, circleVertices(circleRad), circleRad, Color.clear, Tmp.c1.set(Draw.getColor()).a(e.fout(Interp.pow10Out)));
                Draw.blend();
                Draw.z(Layer.effect);

                Drawf.light(e.x, e.y, rad * e.fout(Interp.circleOut) * 4f, e.color, 0.7f);
            }).layer(Layer.effect + 0.001f),
            blueStarExplosionCloud = new Effect(60, 500f, b -> {
                float intensity = 6.8f;
                float baseLifetime = 25f + intensity * 11f;
                b.lifetime = 50f + intensity * 65f;

                color(ExoPal.starBlue2);
                alpha(0.7f);
                for(int i = 0; i < 4; i++){
                    rand.setSeed(b.id*2 + i);
                    float lenScl = rand.random(0.4f, 1f);
                    int fi = i;
                    b.scaled(b.lifetime * lenScl, e -> {
                        randLenVectors(e.id + fi - 1, e.fin(Interp.pow10Out), (int)(2.9f * intensity), 22f * intensity, (x, y, in, out) -> {
                            float fout = e.fout(Interp.pow5Out) * rand.random(0.5f, 1f);
                            float rad = fout * ((2f + intensity) * 2.35f);
                            Fill.circle(e.x + x, e.y + y, rad);
                            Drawf.light(e.x + x, e.y + y, rad * 2.5f, ExoPal.starBlue, 0.5f);
                        });
                    });
                }
        b.scaled(baseLifetime, e -> {
            Draw.color();
            e.scaled(5 + intensity * 2f, i -> {
                stroke((3.1f + intensity/5f) * i.fout());
                Lines.circle(e.x, e.y, (3f + i.fin() * 14f) * intensity);
                Drawf.light(e.x, e.y, i.fin() * 14f * 2f * intensity, Color.white, 0.9f * e.fout());
            });

            color(Pal.lighterOrange, Pal.reactorPurple, e.fin());
            stroke((2f * e.fout()));

            Draw.z(Layer.effect + 0.001f);
            randLenVectors(e.id + 1, e.finpow() + 0.001f, (int)(8 * intensity), 28f * intensity, (x, y, in, out) -> {
                lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), 1f + out * 4 * (4f + intensity));
                Drawf.light(e.x + x, e.y + y, (out * 4 * (3f + intensity)) * 3.5f, Draw.getColor(), 0.8f);
            });
        });
    }),
            redStarSwirl = new SwirlEffect(75.0F, 8, 3F, 100.0F, 380.0F, true).layer(110.005F),
            yellowStarSwirl = new SwirlEffect(90.0F, 10, 3F, 120.0F, 480.0F, true).layer(110.005F),
            whiteStarSwirl = new SwirlEffect(90.0F, 8, 3F, 120.0F, 480.0F, true).layer(110.005F),
            starngeStarSwirl = new SwirlEffect(70.0F, 18, 1.7F, 140.0F, 680.0F, true).layer(110.005F),
            blueStarSwirl = new SwirlEffect(90.0F, 10, 3F, 120.0F, 480.0F, true).layer(110.005F),
            darkBlueStarSwirl = new SwirlEffect(120.0F, 9, 5.5F, 140.0F, 780.0F, true).layer(110.005F),
            smolSwirl = new SwirlEffect(120.0F, 6, 2.5F, 140.0F, 780.0F, true).layer(110.005F),

            strangeStarSparks = new Effect(16f, e -> {
                color(Color.white, e.color, e.fin());
                stroke(e.fout() * 1.1f + 0.5f);

                randLenVectors(e.id, 3, 140f * e.fin(), e.rotation, 0f, (x, y) -> {
                    lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), e.fslope() * 5f + 0.5f);
                });
            }),
            redBallfire = new Effect(25f, e -> {
                color(ExoPal.cronusRedlight, ExoPal.cronusRedDark, e.fin());

                randLenVectors(e.id, 2, 2f + e.fin() * 7f, (x, y) -> {
                    Fill.circle(e.x + x, e.y + y, 0.2f + e.fout() * 1.5f);
                });
            }),
            starShockWave = new Effect(95f, e -> {
                float shock = 50f * (1f + e.fin(Interp.pow5Out) * 2f) + (e.fin() * 50f);
                color(e.color);
                Lines.stroke(6f * e.fout(Interp.pow5Out));
                Lines.circle(e.x, e.y, shock);
                float ang = rand.random(360f);
                Vec2 v = Tmp.v1.trns(ang, shock).add(e.x, e.y);
                Drawf.tri(v.x, v.y, 6f * e.fout(), (70f * e.fin()), ang + 180f);
            }),
            supernovaBlast = new Effect(30, e -> {
                color(e.color);
                stroke(e.fout() * 1.6f);

                randLenVectors(e.id, 18, e.finpow() * 27f, e.rotation, 60f, (x, y) -> {
                    float ang = Mathf.angle(x, y);
                    lineAngle(e.x + x, e.y + y, ang, e.fout() * 18 + 1f);
                });
            }),
            //test
             railgunSpark = new Effect(26f, e -> {
                 color(e.color);
                 float length = !(e.data instanceof Float) ? 70f : (Float)e.data;

                 randLenVectors(e.id, 7, length, e.rotation, 0f, (x, y) -> {
                     lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), e.fout() * 9f);
                 });
             }),
             hitEmpColorSpark = new Effect(40, e -> {
                 color(e.color);
                 stroke(e.fout() * 1.6f);

                 randLenVectors(e.id, 18, e.finpow() * 27f, e.rotation, 360f, (x, y) -> {
                     float ang = Mathf.angle(x, y);
                     lineAngle(e.x + x, e.y + y, ang, e.fout() * 6 + 1f);
                 });
             }),
             blastExplosionColor = new Effect(22, e -> {
                 color(e.color);

                 e.scaled(6, i -> {
                     stroke(3f * i.fout());
                     Lines.circle(e.x, e.y, 3f + i.fin() * 15f);
                 });

                 color(Color.gray);

                 randLenVectors(e.id, 5, 2f + 23f * e.finpow(), (x, y) -> {
                     Fill.circle(e.x + x, e.y + y, e.fout() * 4f + 0.5f);
                 });

                 color(e.color);
                 stroke(e.fout());

                 randLenVectors(e.id + 1, 4, 1f + 23f * e.finpow(), (x, y) -> {
                     lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), 1f + e.fout() * 3f);
                 });

                 Drawf.light(e.x, e.y, 45f, e.color, 0.8f * e.fout());
             }),
            blastcolor = new Effect(40f, 600,e -> {
                color(e.color);
                stroke(e.fout() * 3.7f);
                circle(e.x, e.y, e.fin(pow3Out) * 240 + 15);
                rand.setSeed(e.id);
                randLenVectors(e.id, 12, 8 + 60 * e.fin(Interp.pow5Out), (x, y) -> Fill.circle(e.x + x, e.y + y, e.fout(Interp.circleIn) * (6f + rand.random(6f))));
                Drawf.light(e.x, e.y, e.fout() * 320f, e.color, 0.7f);
            }),

    colorBombSmall = new Effect(40f, 100f, e -> {
        color(e.color);
        stroke(e.fout());
        float circleRad = 4f + e.finpow() * 30f;
        Lines.circle(e.x, e.y, circleRad);

        color(e.color);
        for(int i = 0; i < 4; i++){
            Drawf.tri(e.x, e.y, 3f, 30f * e.fout(), i*90);
        }

        color();
        for(int i = 0; i < 4; i++){
            Drawf.tri(e.x, e.y, 1.3f, 10f * e.fout(), i*90);
        }

        Drawf.light(e.x, e.y, circleRad * 1.6f, e.color, e.fout());
    });
}
