package pl.librus.client.timetable;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.viewholders.FlexibleViewHolder;
import pl.librus.client.R;
import pl.librus.client.api.Lesson;

/**
 * Created by szyme on 26.12.2016. librus-client
 */

public class TabLessonItem extends AbstractFlexibleItem<TabLessonItem.TabLessonItemViewHolder> implements Comparable<TabLessonItem> {

    private Lesson lesson;
    private Context context;

    public TabLessonItem(Lesson lesson, Context context) {
        this.lesson = lesson;
        this.context = context;
    }

    @Override
    public TabLessonItemViewHolder createViewHolder(FlexibleAdapter adapter, LayoutInflater inflater, ViewGroup parent) {
        return new TabLessonItemViewHolder(inflater.inflate(R.layout.lesson_item, parent, false), adapter);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TabLessonItem that = (TabLessonItem) o;

        return lesson.equals(that.lesson);

    }

    @Override
    public int hashCode() {
        return lesson.hashCode();
    }

    /**
     * The Adapter and the Payload are provided to get more specific information from it.
     */
    @Override
    public void bindViewHolder(FlexibleAdapter adapter, TabLessonItemViewHolder holder, int position,
                               List payloads) {

        holder.subject.setText(lesson.getSubject().getName());
        holder.teacher.setText(lesson.getTeacher().getName());
        holder.lessonNumber.setText(String.valueOf(lesson.getLessonNumber()));

        if (lesson.isCanceled()) {
            //lesson canceled
            holder.badge.setVisibility(View.VISIBLE);
            holder.badgeText.setText(R.string.canceled);
            holder.badgeIcon.setImageDrawable(context.getDrawable(R.drawable.ic_cancel_black_24dp));
        } else if (lesson.isSubstitution()) {
            //substitution
            holder.badge.setVisibility(View.VISIBLE);
            holder.badgeText.setText(R.string.substitution);
            holder.badgeIcon.setImageDrawable(context.getDrawable(R.drawable.ic_swap_horiz_black_24dp));
        } else {
            //normal lesson
            holder.badge.setVisibility(View.GONE);
        }
    }

    @Override
    public int compareTo(@NonNull TabLessonItem tabLessonItem) {
        return Integer.compare(lesson.getLessonNumber(), tabLessonItem.lesson.getLessonNumber());
    }

    /**
     * The LessonItemViewHolder used by this item.
     * Extending from FlexibleViewHolder is recommended especially when you will use
     * more advanced features.
     */
    class TabLessonItemViewHolder extends FlexibleViewHolder {
        final TextView subject, teacher, badgeText, lessonNumber;
        final ImageView badgeIcon;
        View badge;

        TabLessonItemViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
            subject = (TextView) itemView.findViewById(R.id.lessonSubject);
            teacher = (TextView) itemView.findViewById(R.id.lessonTeacher);
            badge = itemView.findViewById(R.id.badge);
            badgeIcon = (ImageView) itemView.findViewById(R.id.badgeIcon);
            badgeText = (TextView) itemView.findViewById(R.id.badgeText);
            lessonNumber = (TextView) itemView.findViewById(R.id.lessonNumber);
        }
    }
}
