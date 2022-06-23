package lee.code.discord.commands;

import lee.code.discord.GoldmanDiscord;
import lee.code.discord.files.ConfigFile;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.Modal;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.ModalMapping;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;

public class SupportCommand extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {

        if (event.getName().equals("support")) {

            TextInput description = TextInput.create("support-description", "Description", TextInputStyle.PARAGRAPH)
                    .setMinLength(10)
                    .setMaxLength(4000)
                    .setRequired(true)
                    .setPlaceholder("Let us know if you have any questions.")
                    .build();

            Modal modal = Modal.create("support-modal", "Support Request")
                    .addActionRow(description)
                    .build();

            event.replyModal(modal).queue();
        }
    }


    @Override
    public void onModalInteraction(@NotNull ModalInteractionEvent e) {
        if (e.getModalId().equals("support-modal")) {
            ModalMapping descriptionModal = e.getValue("support-description");
            Member member = e.getMember();
            Guild guild = GoldmanDiscord.getPlugin().getGuild();
            Category category = guild.getCategoryById(ConfigFile.SUPPORT_CATEGORY_ID.getString(null));
            if (descriptionModal != null && category != null && member != null) {
                String description = descriptionModal.getAsString();
                String username = e.getUser().getName();
                int number = ConfigFile.SUPPORT_NUMBER.getValue();
                category.createTextChannel(number + "-Support-Request")
                        .addPermissionOverride(member, EnumSet.of(Permission.VIEW_CHANNEL), null)
                        .addPermissionOverride(guild.getPublicRole(), null, EnumSet.of(Permission.VIEW_CHANNEL))
                        .queue(textChannel -> textChannel.sendMessage("**User:** " + username + "\n" +
                                "**Description:**\n" +
                                description).queue());
                ConfigFile.SUPPORT_NUMBER.setValue(number + 1);
                e.reply("Thank you for opening a support request. A staff member will reply to your new channel under support-requests as soon as possible.").setEphemeral(true).queue();
            }
        }
    }
}
