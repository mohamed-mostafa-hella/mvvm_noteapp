package com.example.mvvm_noteapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.noteholder> {

    private List<Note> notes=new ArrayList<>();
    private OnitemClickListener listener;


    @NonNull
    @Override
    public noteholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.noteitem,viewGroup , false);
        return new noteholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull noteholder noteholder, int i) {
        Note current_node=notes.get(i);
        noteholder.textViewTitle.setText(current_node.getTitle());
        noteholder.textViewDescription.setText(current_node.getDescription());
        noteholder.textViewPriority.setText(String.valueOf(current_node.getPriority()));
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(List<Note> notes){
        this.notes=notes;
        notifyDataSetChanged();
    }

    public Note getNote(int i){
        return notes.get(i);
    }

    class noteholder extends RecyclerView.ViewHolder{

        private TextView textViewTitle , textViewDescription , textViewPriority;

        public noteholder(@NonNull View itemView) {
            super(itemView);

            textViewTitle=itemView.findViewById(R.id.text_view_title);
            textViewDescription=itemView.findViewById(R.id.text_view_description);
            textViewPriority=itemView.findViewById(R.id.text_view_priority);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos=getAdapterPosition();
                    if(listener != null && pos != RecyclerView.NO_POSITION){
                        listener.onItemClick(notes.get(pos));
                    }
                }
            });
        }
    }

    public interface OnitemClickListener{
        void onItemClick(Note note);
    }

    public void setOnItemClickListener(OnitemClickListener listener){
        this.listener=listener;
    }
}
