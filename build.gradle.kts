
import com.possible_triangle.gradle.features.publishing.DependencyBuilder
import net.minecraftforge.gradle.common.util.MinecraftExtension

val mc_version: String by extra
val registrate_version: String by extra
val create_version: String by extra
val flywheel_version: String by extra
val jei_version: String by extra
val supplementaries_version: String by extra
val moonlight_lib_version: String by extra
val dye_depot_version: String by extra
val another_furniture_version: String by extra
val comforts_version: String by extra
val supplementaries_squared_version: String by extra
val quark_version: String by extra
val zeta_version: String by extra
val jade_version: String by extra
val farmers_delight_version: String by extra
val clayworks_version: String by extra
val blueprint_version: String by extra
val alexs_caves_version: String by extra
val domestication_innovation_version: String by extra
val citadel_version: String by extra

plugins {
    id("com.possible-triangle.gradle") version ("0.2.5")
}

withKotlin()

forge {
   if(env.isCI) {
       // alex's caves & domestic innovation break when running in dev with mixins enabled
       enableMixins()
   }

    dataGen(
        existingMods = listOf(
            "dye_depot",
            "another_furniture",
            "supplementaries",
            "create",
            "comforts",
            "quark",
            "suppsquared",
            "farmersdelight",
            "domesticationinnovation"
        )
    )

    includesMod("com.tterrag.registrate:Registrate:${registrate_version}")
}

// needed because of flywheel accessing the config too early
configure<MinecraftExtension> {
    runs {
        forEach {
            it.property("production", "true")
        }
    }
}

repositories {
    modrinthMaven()
    localMaven(project)

    maven {
        url = uri("https://maven.blamejared.com/")
        content {
            includeGroup("mezz.jei")
        }
    }
    maven {
        url = uri("https://maven.tterrag.com/")
        content {
            includeGroup("com.simibubi.create")
            includeGroup("com.jozufozu.flywheel")
            includeGroup("com.tterrag.registrate")
        }
    }
}

dependencies {
    modImplementation("com.simibubi.create:create-${mc_version}:${create_version}:slim") { isTransitive = false }
    modImplementation("com.jozufozu.flywheel:flywheel-forge-${mc_version}:${flywheel_version}")
    modImplementation("maven.modrinth:another-furniture:${another_furniture_version}")
    modImplementation("maven.modrinth:comforts:${comforts_version}")
    modImplementation("maven.modrinth:moonlight:${moonlight_lib_version}")
    modImplementation("maven.modrinth:supplementaries:${supplementaries_version}")
    modImplementation("maven.modrinth:supplementaries-squared:${supplementaries_squared_version}")
    modImplementation("maven.modrinth:quark:${quark_version}")
    modImplementation("maven.modrinth:zeta:${zeta_version}")
    modImplementation("maven.modrinth:farmers-delight:${farmers_delight_version}")
    modImplementation("maven.modrinth:clayworks:${clayworks_version}")
    modImplementation("maven.modrinth:blueprint:${blueprint_version}")
    modImplementation("maven.modrinth:domestication-innovation:${domestication_innovation_version}")
    modImplementation("maven.modrinth:alexs-caves:${alexs_caves_version}")

    if (!env.isCI) {
        modRuntimeOnly("mezz.jei:jei-${mc_version}-forge:${jei_version}")
        modRuntimeOnly("maven.modrinth:jade:${jade_version}")
        modRuntimeOnly("maven.modrinth:dye-depot:${dye_depot_version}")
        modRuntimeOnly("maven.modrinth:citadel:${citadel_version}")
    }
}

tasks.withType<Jar> {
    exclude("**/*.xcf")
}

enablePublishing {
    githubPackages()
}

fun DependencyBuilder.addDependencies() {
    required("dye-depot")
    optional("create")
    optional("another-furniture")
    optional("comforts")
    optional("clayworks")
    optional("farmers-delight")
    optional("quark")
    optional("domestication-innovation")
    optional("supplementaries")
    optional("supplementaries-squared")
    optional("alexs-caves")
}

uploadToCurseforge {
    dependencies {
        addDependencies()
    }
}

uploadToModrinth {
    dependencies {
        addDependencies()
    }

    syncBodyFromReadme()
}

enableSonarQube()