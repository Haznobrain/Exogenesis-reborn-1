package Exogenesis.content;

import Exogenesis.graphics.ExogenesisPal;
import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import mindustry.ai.*;
import mindustry.ai.types.*;
import mindustry.annotations.Annotations.*;
import mindustry.entities.*;
import mindustry.entities.abilities.*;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.*;
import mindustry.entities.part.*;
import mindustry.entities.pattern.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.type.ammo.*;
import mindustry.type.unit.*;
import mindustry.type.weapons.*;
import mindustry.world.meta.*;
import mindustry.content.*;
import mindustry.entities.units.*;

public class ExoUnitTypes {
    public static UnitType orion;
    public static void load() {
        orion = new UnitType("orion") {{
            constructor = LegUnit::create;
            outlineColor = Color.valueOf("36363c");
            speed = 0.7f;
            hitSize = 10f;
            health = 360f;
            faceTarget = true;
            armor = 2;
            shadowElevation = 0.1f;
            targetAir = false;
            allowLegStep = true;
            hovering = true;
            rotateSpeed = 0.3f;
            legMoveSpace = 1;
            legMaxLength = 1.1f;
            legMinLength = 0.2f;
            legLengthScl = 0.96f;
            legForwardScl = 1.1f;
            legPhysicsLayer = false;
            legGroupSize = 4;
            legCount = 8;
            legExtension = -2;
            legContinuousMove = true;
            lockLegBase = true;
            rippleScale = 0.2f;
            legBaseOffset = 5;
            legLength = 9;

            weapons.add(new Weapon("orion") {{
                reload = 100f;
                mirror = false;
                x = 0;
                shootSound = Sounds.artillery;
                showStatSprite = false;
                smoothReloadSpeed = 0.15f;
                shootWarmupSpeed = 0.05f;
                minWarmup = 0.9f;
                shootY = 3;
                recoil = 0;
                shake = 1f;

                parts.addAll(
                        new RegionPart("-blade") {{
                            mirror = true;
                            heatColor = Color.valueOf("66B1FF");
                            progress = PartProgress.warmup;
                            heatProgress = PartProgress.warmup;
                            moveRot = -22f;
                        }},
                        new ShapePart() {{
                            mirror = true;
                            progress = PartProgress.warmup;
                            hollow = true;
                            circle = true;
                            y = 3f;
                            color = Color.valueOf("66B1FF");
                            stroke = 0f;
                            strokeTo = 1f;
                            radiusTo = 3f;
                            radius = 3f;
                        }},
                        new HaloPart() {{
                            y = 3f;
                            progress = haloProgress;
                            radius = 2f;
                            tri = true;
                            color = Color.valueOf("66B1FF");
                            haloRotateSpeed = -1f;
                            haloRadius = 3f;
                            haloRadiusTo = 3f;
                            stroke = 0f;
                            strokeTo = 2f;
                            shapes = 2;
                            triLengthTo = 3f;
                            triLength = 0f;
                        }},
                        new HaloPart() {{
                            y = 3f;
                            progress = haloProgress;
                            radius = 2f;
                            tri = true;
                            color = ExogenesisPal.genesisDark;
                            haloRotateSpeed = 1f;
                            haloRadius = 3f;
                            haloRadiusTo = 3f;
                            stroke = 0f;
                            strokeTo = 2f;
                            shapes = 2;
                            triLengthTo = 3f;
                            triLength = 0f;
                        }}
                        );
                shootType = new EmpBulletType(8f, 70f) {{
                    width = 8f;
                    height = 11f;
                    sprite = circle-bullet;
                    frontColor = Color.white;
                    backColor = hitColor = trailColor = ExogenesisPal.genesisDark;
                    lifetime = 60f;
                    speed = 6f;
                    damage = 25f;
                    recoil = 0.6f;
                    splashDamage = 15;
                    splashDamageRadius = 40;
                    shrinkY = shrinkX = 0;
                    radius = 40f;
                    timeIncrease = 0.5f;
                    powerDamageScl = 0.3f;
                    powerSclDecrease = 0.1f;
                    unitDamageScl = 0.3f;
                    status = StatusEffects.freezing;
                    statusDuration = 100;
                    shootEffect = Fx.lightningShoot;
                    homingPower = 0.0678f;
                    homingRange = 40;
                    trailLength = 10f;
                    trailWidth = 2f;
                    trailChance = 0.3f;
                    trailEffect = new ParticleEffect() {{
                    particles = 13;
                    length = 2.5f;
                    baseLength = 2.5f;
                    lifetime = 20f;
                    colorFrom = colorTo = trailColor;
                    sizeFrom = 5f;
                    sizeTo = 0f;
                    }};
                }};
            }});
        }};
    }
}