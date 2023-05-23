package james.LineBot.handler;

import james.LineBot.repository.LessonRepository;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.action.MessageAction;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.TemplateMessage.TemplateMessageBuilder;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.message.template.ConfirmTemplate;
import com.linecorp.bot.model.message.template.Template;
import com.linecorp.bot.model.response.BotApiResponse;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import static java.util.Collections.singletonList;


@Slf4j
@LineMessageHandler
public class LineMsgHandler {

    @Autowired
    private LineMessagingClient lineMessagingClient;

    @Autowired
    private LessonRepository lessonRepository;

    private int lessonCount;
    private Random random = new Random();

    @PostConstruct
    public void init() {
        log.info(" *** init *** ");
        updateLessonCount();
    }

    private void reply(@NonNull String replyToken, @NonNull Message message) {
        reply(replyToken, singletonList(message));
    }

    private void reply(@NonNull String replyToken, @NonNull List<Message> messages) {
        reply(replyToken, messages, false);
    }

    private void reply(@NonNull String replyToken,
                       @NonNull List<Message> messages,
                       boolean notificationDisabled) {
        try {
            BotApiResponse apiResponse = lineMessagingClient
                    .replyMessage(new ReplyMessage(replyToken, messages, notificationDisabled))
                    .get();
            log.info("Sent messages: {}", apiResponse);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }


    @EventMapping
    public void handleTextMessageEvent(MessageEvent<TextMessageContent> event) throws Exception {
        TextMessageContent message = event.getMessage();
        handleTextContent(event.getReplyToken(), event, message);


//        // 這邊做的就是簡單的 echo
//        int randomNum = random.nextInt(lessonCount);
//
//        log.info("lessonCount:> " + lessonCount + ", randomNum:> " + randomNum);
//        if (randomNum == 0) randomNum = 1;
//
//
//
//
//        TemplateMessage.builder().build().getAltText()
//        return new TextMessage(randomNum + " - " + lessonRepository.findByIndex(randomNum).getContent());
    }

    @EventMapping
    public void handleDefaultMessageEvent(Event event) {
        // 就是加入聊天室, 離開聊天室, 還有一些有的沒的事件
//        System.out.println("handleDefaultMessageEvent, event: " + event);
    }


    private void handleTextContent(String replyToken, Event event, TextMessageContent content)
            throws Exception {
        final String text = content.getText();

        log.info("Got text message from replyToken:{}: text:{} emojis:{}", replyToken, text,
                content.getEmojis());
        switch (text) {
//            case "profile": {
//                log.info("Invoking 'profile' command: source:{}",
//                        event.getSource());
//                final String userId = event.getSource().getUserId();
//                if (userId != null) {
//                    if (event.getSource() instanceof GroupSource) {
//                        lineMessagingClient
//                                .getGroupMemberProfile(((GroupSource) event.getSource()).getGroupId(), userId)
//                                .whenComplete((profile, throwable) -> {
//                                    if (throwable != null) {
//                                        this.replyText(replyToken, throwable.getMessage());
//                                        return;
//                                    }
//
//                                    this.reply(
//                                            replyToken,
//                                            Arrays.asList(new TextMessage("(from group)"),
//                                                    new TextMessage(
//                                                            "Display name: " + profile.getDisplayName()),
//                                                    new ImageMessage(profile.getPictureUrl(),
//                                                            profile.getPictureUrl()))
//                                    );
//                                });
//                    } else {
//                        lineMessagingClient
//                                .getProfile(userId)
//                                .whenComplete((profile, throwable) -> {
//                                    if (throwable != null) {
//                                        this.replyText(replyToken, throwable.getMessage());
//                                        return;
//                                    }
//
//                                    this.reply(
//                                            replyToken,
//                                            Arrays.asList(new TextMessage(
//                                                            "Display name: " + profile.getDisplayName()),
//                                                    new TextMessage("Status message: "
//                                                            + profile.getStatusMessage()))
//                                    );
//
//                                });
//                    }
//                } else {
//                    this.replyText(replyToken, "Bot can't use profile API without user ID");
//                }
//                break;
//            }
//            case "bye": {
//                Source source = event.getSource();
//                if (source instanceof GroupSource) {
//                    this.replyText(replyToken, "Leaving group");
//                    lineMessagingClient.leaveGroup(((GroupSource) source).getGroupId()).get();
//                } else if (source instanceof RoomSource) {
//                    this.replyText(replyToken, "Leaving room");
//                    lineMessagingClient.leaveRoom(((RoomSource) source).getRoomId()).get();
//                } else {
//                    this.replyText(replyToken, "Bot can't leave from 1:1 chat");
//                }
//                break;
//            }
//            case "group_summary": {
//                Source source = event.getSource();
//                if (source instanceof GroupSource) {
//                    GroupSummaryResponse groupSummary = lineMessagingClient.getGroupSummary(
//                            ((GroupSource) source).getGroupId()).get();
//                    this.replyText(replyToken, "Group summary: " + groupSummary);
//                } else {
//                    this.replyText(replyToken, "You can't use 'group_summary' command for "
//                            + source);
//                }
//                break;
//            }
//            case "group_member_count": {
//                Source source = event.getSource();
//                if (source instanceof GroupSource) {
//                    GroupMemberCountResponse groupMemberCountResponse = lineMessagingClient.getGroupMemberCount(
//                            ((GroupSource) source).getGroupId()).get();
//                    this.replyText(replyToken, "Group member count: "
//                            + groupMemberCountResponse.getCount());
//                } else {
//                    this.replyText(replyToken, "You can't use 'group_member_count' command  for "
//                            + source);
//                }
//                break;
//            }
//            case "room_member_count": {
//                Source source = event.getSource();
//                if (source instanceof RoomSource) {
//                    RoomMemberCountResponse roomMemberCountResponse = lineMessagingClient.getRoomMemberCount(
//                            ((RoomSource) source).getRoomId()).get();
//                    this.replyText(replyToken, "Room member count: "
//                            + roomMemberCountResponse.getCount());
//                } else {
//                    this.replyText(replyToken, "You can't use 'room_member_count' command  for "
//                            + source);
//                }
//                break;
//            }
//            case "confirm": {
//                ConfirmTemplate confirmTemplate = new ConfirmTemplate(
//                        "Do it?",
//                        new MessageAction("Yes", "Yes!"),
//                        new MessageAction("No", "No!")
//                );
//                TemplateMessage templateMessage = new TemplateMessage("Confirm alt text", confirmTemplate);
//                this.reply(replyToken, templateMessage);
//                break;
//            }
//            case "buttons": {
//                URI imageUrl = createUri("/static/buttons/1040.jpg");
//                ButtonsTemplate buttonsTemplate = new ButtonsTemplate(
//                        imageUrl,
//                        "My button sample",
//                        "Hello, my button",
//                        Arrays.asList(
//                                new URIAction("Go to line.me",
//                                        URI.create("https://line.me"), null),
//                                new PostbackAction("Say hello1",
//                                        "hello こんにちは"),
//                                new PostbackAction("言 hello2",
//                                        "hello こんにちは",
//                                        "hello こんにちは"),
//                                new MessageAction("Say message",
//                                        "Rice=米")
//                        ));
//                TemplateMessage templateMessage = new TemplateMessage("Button alt text", buttonsTemplate);
//                this.reply(replyToken, templateMessage);
//                break;
//            }
//            case "carousel": {
//                URI imageUrl = createUri("/static/buttons/1040.jpg");
//                CarouselTemplate carouselTemplate = new CarouselTemplate(
//                        Arrays.asList(
//                                new CarouselColumn(imageUrl, "hoge", "fuga", Arrays.asList(
//                                        new URIAction("Go to line.me",
//                                                URI.create("https://line.me"), null),
//                                        new URIAction("Go to line.me",
//                                                URI.create("https://line.me"), null),
//                                        new PostbackAction("Say hello1",
//                                                "hello こんにちは")
//                                )),
//                                new CarouselColumn(imageUrl, "hoge", "fuga", Arrays.asList(
//                                        new PostbackAction("言 hello2",
//                                                "hello こんにちは",
//                                                "hello こんにちは"),
//                                        new PostbackAction("言 hello2",
//                                                "hello こんにちは",
//                                                "hello こんにちは"),
//                                        new MessageAction("Say message",
//                                                "Rice=米")
//                                )),
//                                new CarouselColumn(imageUrl, "Datetime Picker",
//                                        "Please select a date, time or datetime", Arrays.asList(
//                                        DatetimePickerAction.OfLocalDatetime
//                                                .builder()
//                                                .label("Datetime")
//                                                .data("action=sel")
//                                                .initial(LocalDateTime.parse("2017-06-18T06:15"))
//                                                .min(LocalDateTime.parse("1900-01-01T00:00"))
//                                                .max(LocalDateTime.parse("2100-12-31T23:59"))
//                                                .build(),
//                                        DatetimePickerAction.OfLocalDate
//                                                .builder()
//                                                .label("Date")
//                                                .data("action=sel&only=date")
//                                                .initial(LocalDate.parse("2017-06-18"))
//                                                .min(LocalDate.parse("1900-01-01"))
//                                                .max(LocalDate.parse("2100-12-31"))
//                                                .build(),
//                                        DatetimePickerAction.OfLocalTime
//                                                .builder()
//                                                .label("Time")
//                                                .data("action=sel&only=time")
//                                                .initial(LocalTime.parse("06:15"))
//                                                .min(LocalTime.parse("00:00"))
//                                                .max(LocalTime.parse("23:59"))
//                                                .build()
//                                ))
//                        ));
//                TemplateMessage templateMessage = new TemplateMessage("Carousel alt text", carouselTemplate);
//                this.reply(replyToken, templateMessage);
//                break;
//            }
//            case "image_carousel": {
//                URI imageUrl = createUri("/static/buttons/1040.jpg");
//                ImageCarouselTemplate imageCarouselTemplate = new ImageCarouselTemplate(
//                        Arrays.asList(
//                                new ImageCarouselColumn(imageUrl,
//                                        new URIAction("Goto line.me",
//                                                URI.create("https://line.me"), null)
//                                ),
//                                new ImageCarouselColumn(imageUrl,
//                                        new MessageAction("Say message",
//                                                "Rice=米")
//                                ),
//                                new ImageCarouselColumn(imageUrl,
//                                        new PostbackAction("言 hello2",
//                                                "hello こんにちは",
//                                                "hello こんにちは")
//                                )
//                        ));
//                TemplateMessage templateMessage = new TemplateMessage("ImageCarousel alt text",
//                        imageCarouselTemplate);
//                this.reply(replyToken, templateMessage);
//                break;
//            }
//            case "imagemap":
//                //            final String baseUrl,
//                //            final String altText,
//                //            final ImagemapBaseSize imagemapBaseSize,
//                //            final List<ImagemapAction> actions) {
//                this.reply(replyToken, ImagemapMessage
//                        .builder()
//                        .baseUrl(createUri("/static/rich"))
//                        .altText("This is alt text")
//                        .baseSize(new ImagemapBaseSize(1040, 1040))
//                        .actions(Arrays.asList(
//                                URIImagemapAction.builder()
//                                        .linkUri("https://store.line.me/family/manga/en")
//                                        .area(new ImagemapArea(0, 0, 520, 520))
//                                        .build(),
//                                URIImagemapAction.builder()
//                                        .linkUri("https://store.line.me/family/music/en")
//                                        .area(new ImagemapArea(520, 0, 520, 520))
//                                        .build(),
//                                URIImagemapAction.builder()
//                                        .linkUri("https://store.line.me/family/play/en")
//                                        .area(new ImagemapArea(0, 520, 520, 520))
//                                        .build(),
//                                MessageImagemapAction.builder()
//                                        .text("URANAI!")
//                                        .area(new ImagemapArea(520, 520, 520, 520))
//                                        .build()
//                        ))
//                        .build());
//                break;
//            case "imagemap_video":
//                this.reply(replyToken, ImagemapMessage
//                        .builder()
//                        .baseUrl(createUri("/static/imagemap_video"))
//                        .altText("This is an imagemap with video")
//                        .baseSize(new ImagemapBaseSize(722, 1040))
//                        .video(
//                                ImagemapVideo.builder()
//                                        .originalContentUrl(
//                                                createUri("/static/imagemap_video/originalContent.mp4"))
//                                        .previewImageUrl(
//                                                createUri("/static/imagemap_video/previewImage.jpg"))
//                                        .area(new ImagemapArea(40, 46, 952, 536))
//                                        .externalLink(
//                                                new ImagemapExternalLink(
//                                                        URI.create("https://example.com/see_more.html"),
//                                                        "See More")
//                                        )
//                                        .build()
//                        )
//                        .actions(singletonList(
//                                MessageImagemapAction.builder()
//                                        .text("NIXIE CLOCK")
//                                        .area(new ImagemapArea(260, 600, 450, 86))
//                                        .build()
//                        ))
//                        .build());
//                break;
//            case "flex":
//                this.reply(replyToken, new ExampleFlexMessageSupplier().get());
//                break;
//            case "quickreply":
//                this.reply(replyToken, new MessageWithQuickReplySupplier().get());
//                break;
//            case "no_notify":
//                this.reply(replyToken,
//                        singletonList(new TextMessage("This message is send without a push notification")),
//                        true);
//                break;
//            case "redelivery":
//                this.reply(replyToken,
//                        singletonList(new TextMessage("webhookEventId=" + event.getWebhookEventId()
//                                + " deliveryContext.isRedelivery=" + event.getDeliveryContext().getIsRedelivery())
//                        ));
//                break;
//            case "icon":
//                this.reply(replyToken,
//                        TextMessage.builder()
//                                .text("Hello, I'm cat! Meow~")
//                                .sender(Sender.builder()
//                                        .name("Cat")
//                                        .iconUrl(createUri("/static/icon/cat.png"))
//                                        .build())
//                                .build());
//                break;
            case "1":
                ConfirmTemplate confirmTemplate = new ConfirmTemplate(
                        "Do it?",
                        new MessageAction("Yes", "Yes!"),
                        new MessageAction("No", "No!")
                );
                TemplateMessage templateMessage = new TemplateMessage("Confirm alt text", confirmTemplate);
                this.reply(replyToken, templateMessage);
                break;
            default:
                log.info("Returns echo message {}: {}", replyToken, text);
                int randomNum = random.nextInt(lessonCount);

                log.info("lessonCount:> " + lessonCount + ", randomNum:> " + randomNum);
                if (randomNum == 0) randomNum = 1;
                String resp = randomNum + " - " + lessonRepository.findByIndex(randomNum).getContent();

                this.replyText(
                        replyToken,
                        resp
                );
                break;
        }
    }


    private void replyText(@NonNull String replyToken, @NonNull String message) {
        if (replyToken.isEmpty()) {
            throw new IllegalArgumentException("replyToken must not be empty");
        }
        if (message.length() > 1000) {
            message = message.substring(0, 1000 - 2) + "……";
        }
        this.reply(replyToken, new TextMessage(message));
    }

    @Scheduled(cron = "0 * * * * ?")
    public void updateData() {
        log.info("Before::updateData:> " + lessonCount);
        updateLessonCount();
        log.info("After::updateData:> " + lessonCount);
    }

    public void updateLessonCount() {
        lessonCount = lessonRepository.countByStatus(true);
    }
}
