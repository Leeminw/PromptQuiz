package com.ssafy.apm.sdw.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SdwRequestDto {
    @Builder.Default
    private String prompt = "(HDR, UHD, 64K), (masterpiece, best quality, high quality), anime style, " +
            "(hyper detailed, ultra detailed, highly detailed), " +
            "(hi-top fade:1.3), universe, milkyway, scenary, landscape, sunrise, " +
            "dark theme, fantasy world, wallpaper, professional, " +
            "soothing tones, clean pastel color tones, muted colors, high contrast, " +
            "(natural skin texture, hyperrealism, soft light, sharp), pureerosface_v1";
    @Builder.Default
    private String negative_prompt = "EasyNegative, AuroraNegative, ng_deepnegative_v1_75t, bad-image-v2-39000, KHFB, " +
                    "(worst quality:2), (bad quality:2), (poor quality:2), (low quality:2), (normal quality:2), " +
                    "(worst details:2), (bad details:2), (poor details:2), (low details:2), (normal details:2), " +

                    "bad-artist, bad-artist-anime, poorly drawn, poorly sketched, " +
                    "poorly drawn by bad-artist, poorly drawn by bad-artist-anime, " +
                    "poorly sketched by bad-artist, poorly sketched by bad-artist-anime, " +

                    "negative_hand-neg, bad-hands-5, badhandv4, extra digits, fewer digits, " +
                    "bad anatomy, bad face, bad body, bad limbs, " +
                    "bad fingers, bad hands, bad arms, bad legs, " +
                    "extra fingers, extra hands, extra arms, extra legs, " +
                    "fewer fingers, fewer hands, fewer arms, fewer legs, " +
                    "missing fingers, missing hands, missing arms, missing legs, " +

                    "poorly drawn anatomy, poorly drawn face, poorly drawn body, poorly drawn limbs," +
                    "poorly drawn fingers, poorly drawn hands, poorly drawn arms, poorly drawn legs," +
                    "poorly sketched anatomy, poorly sketched face, poorly sketched body, poorly sketched limbs," +
                    "poorly sketched fingers, poorly sketched hands, poorly sketched arms, poorly sketched legs," +

                    "(username, artist name, signature, watermark, time signature, timestamp, copyright name, copyright:1.4)," +
                    "(nsfw, uncensored, nude, nipples:2), (monochrome, grayscale, lowres, blurry, fuzzy:1.2), (jpeg, jpeg artifacts:1.2)" +
                    "(ugly:2), text, logo";
    @Builder.Default
    private Integer steps = 10;
    @Builder.Default
    private Double cfg_scale = 9.0;
    @Builder.Default
    private Integer width = 360;
    @Builder.Default
    private Integer height = 640;
    @Builder.Default
    private Double denoising_strength = 0.25;
    @Builder.Default
    private Map<String, String> override_settings = Map.of(
            "sd_model_checkpoint", "animePastelDream_softBakedVae",
            "sd_lora", "more_details",
            "sd_vae", "vaeFtMse840000Ema_v100.pt"
    );
    @Builder.Default
    private Boolean enable_hr = true;
    @Builder.Default
    private Double hr_scale = 2.0;
    @Builder.Default
    private String hr_upscaler = "R-ESRGAN 4x+";
    @Builder.Default
    private Integer hr_second_pass_steps = 8;
    @Builder.Default
    private String sampler_index = "DPM++ SDE Karras";

    public SdwRequestDto updateAnimePrompt(String prompt) {
        this.prompt = "(HDR, UHD, 64K), (best quality, masterpiece), anime style, " + prompt +
                ", professional, (hyper detailed, ultra detailed, highly detailed), fantasy world";
        this.steps = 10;
        this.cfg_scale = 9.0;
        this.denoising_strength = 0.25;
        this.hr_second_pass_steps = 8;
        this.sampler_index = "DPM++ SDE Karras";
        this.override_settings = Map.of(
                "sd_model_checkpoint", "animePastelDream_softBakedVae",
                "sd_lora", "more_details",
                "sd_vae", "vaeFtMse840000Ema_v100.pt"
        );
        return this;
    }

    public SdwRequestDto updateRealismPrompt(String prompt) {
        this.prompt = "(HDR, UHD, 64K), (best quality, masterpiece), hyper realism, " + prompt +
                ", professional, (hyper detailed, ultra detailed, highly detailed), fantasy world";
        this.steps = 28;
        this.cfg_scale = 5.0;
        this.denoising_strength = 0.3;
        this.hr_second_pass_steps = 15;
        this.sampler_index = "DPM++ 2M SDE Karras";
        this.override_settings = Map.of(
                "sd_model_checkpoint", "xxmix9realistic_v40",
                "sd_lora", "more_details",
                "sd_vae", "vaeFtMse840000Ema_v100.pt"
        );
        return this;
    }

    public SdwRequestDto updateDisneyPrompt(String prompt) {
        this.prompt = "(HDR, UHD, 64K), (best quality, masterpiece), anime style, " + prompt +
                ", professional, (hyper detailed, ultra detailed, highly detailed), fantasy world";
        this.steps = 10;
        this.cfg_scale = 9.0;
        this.denoising_strength = 0.25;
        this.hr_second_pass_steps = 8;
        this.sampler_index = "DPM++ SDE Karras";
        this.override_settings = Map.of(
                "sd_model_checkpoint", "disneyPixarCartoonTypeA_v10",
                "sd_lora", "more_details",
                "sd_vae", "vaeFtMse840000Ema_v100.pt"
        );
        return this;
    }

}
