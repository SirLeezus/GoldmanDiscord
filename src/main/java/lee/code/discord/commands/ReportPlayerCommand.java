package lee.code.discord.commands;

import lee.code.discord.GoldmanDiscord;
import lee.code.discord.files.ConfigFile;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.Modal;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.ModalMapping;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;

public class ReportPlayerCommand extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {

        if (event.getName().equals("report-player")) {

            TextInput name = TextInput.create("report-player-name", "Player Accused", TextInputStyle.SHORT)
                    .setMinLength(3)
                    .setMaxLength(16)
                    .setRequired(true)
                    .build();

            TextInput description = TextInput.create("report-player-description", "Description", TextInputStyle.PARAGRAPH)
                    .setMinLength(10)
                    .setMaxLength(4000)
                    .setRequired(true)
                    .setPlaceholder("When possible, provide proof with links to videos or screenshots.")
                    .build();

            Modal modal = Modal.create("report-player-modal", "Report Player")
                    .addActionRow(name)
                    .addActionRow(description)
                    .build();

            event.replyModal(modal).queue();
        }
    }


    @Override
    public void onModalInteraction(@NotNull ModalInteractionEvent e) {
        if (e.getModalId().equals("report-player-modal")) {
            ModalMapping nameModal = e.getValue("report-player-name");
            ModalMapping descriptionModal = e.getValue("report-player-description");
            Member member = e.getMember();
            Guild guild = GoldmanDiscord.getPlugin().getGuild();
            Category category = guild.getCategoryById(ConfigFile.REPORT_PLAYER_CATEGORY_ID.getString(null));
            if (nameModal != null && descriptionModal != null && category != null && member != null) {
                String name = nameModal.getAsString();
                String description = descriptionModal.getAsString();
                String username = e.getUser().getName();
                int number = ConfigFile.REPORT_PLAYER_NUMBER.getValue();
                category.createTextChannel(number + "-Player-Report")
                        .addPermissionOverride(member, EnumSet.of(Permission.VIEW_CHANNEL), null)
                        .addPermissionOverride(guild.getPublicRole(), null, EnumSet.of(Permission.VIEW_CHANNEL))
                        .queue(textChannel -> textChannel.sendMessage("**Reporter:** " + username + "\n" +
                                "**Player Accused:** " + name + "\n" +
                                "**Description:**\n" +
                                description).queue());
                ConfigFile.REPORT_PLAYER_NUMBER.setValue(number + 1);
                e.reply("Thank you for reporting the player " + name + ". A staff member will reply to your new channel under player-reports as soon as possible.").setEphemeral(true).queue();
            }
        }
    }
}
