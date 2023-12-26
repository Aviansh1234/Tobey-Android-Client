package com.example.tobeytravelplanner.functionalClasses;

import androidx.annotation.Nullable;

import com.example.tobeytravelplanner.MainActivity;
import com.example.tobeytravelplanner.R;
import com.example.tobeytravelplanner.objects.Message;
import com.example.tobeytravelplanner.adapters.conversationsAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FirebaseHandler {
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    public void logout(){
        FirebaseAuth.getInstance().signOut();
    }

    public void updateMessages(MainActivity mainActivity, String sessionId, ArrayList<Message> messages) {
        firestore.collection("users").document(FirebaseAuth.getInstance().getUid())
                .collection(sessionId).addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null)
                            return;
                        if (value == null) {
                            return;
                        }
                        for (DocumentChange ds : value.getDocumentChanges()) {
                            if (ds.getType() == DocumentChange.Type.MODIFIED) {
                                Boolean iscopmlete = (Boolean) ds.getDocument().get("complete");
                                if (iscopmlete != null && iscopmlete) {
                                    mainActivity.completedChat();
                                }
                            }
                            if (ds.getType() != DocumentChange.Type.ADDED) {
                                continue;
                            }
                            Boolean iscopmlete = (Boolean) ds.getDocument().get("complete");
                            if (iscopmlete != null && iscopmlete) {
                                mainActivity.completedChat();
                            }
                            Message curr = ds.getDocument().toObject(Message.class);
                            messages.add(curr);
                            mainActivity.adapter.notifyItemChanged(messages.size() - 1);
                            mainActivity.send.setEnabled(!curr.getAuthor().equals("user"));
                            if (!curr.getAuthor().equals("user"))
                                mainActivity.send.setColorFilter(mainActivity.getColor(R.color.blue));
                            else
                                mainActivity.send.setColorFilter(mainActivity.getColor(R.color.grey));
                            mainActivity.messages.scrollToPosition(messages.size() - 1);
                        }
                    }
                });
    }

    public void addSessionToList(String sessionId) {
        firestore.collection("users")
                .document(FirebaseAuth.getInstance().getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        List<String> sessions = (List<String>) documentSnapshot.get("sessions");
                        if (sessions == null) {
                            sessions = new ArrayList<>();
                            sessions.add(sessionId);
                            firestore.collection("users")
                                    .document(FirebaseAuth.getInstance().getUid())
                                    .update("sessions", sessions);
                        } else {
                            sessions.add(sessionId);
                            firestore.collection("users")
                                    .document(FirebaseAuth.getInstance().getUid())
                                    .update("sessions", sessions);
                        }
                    }
                });
    }

    public void hotelSessionExists(String sessionId, Runnable runnable) {
        firestore.collection("users")
                .document(FirebaseAuth.getInstance().getUid())
                .collection(sessionId + " : hotels")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots.size() != 0) {
                            runnable.run();
                        }
                    }
                });
    }

    public void getHotelsFromSession(String sessionId, Runnable runOnRes, String[] ans, ArrayList<String> descs, ArrayList<String>prices) {
        firestore.collection("users")
                .document(FirebaseAuth.getInstance().getUid())
                .collection(sessionId + " : hotels")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot ds : queryDocumentSnapshots) {
                            ans[0] += (String) ds.get("itenaryId");
                            ans[0] += ",";
                            String messageContent = (String)ds.get("messageContent");
                            descs.add(messageContent.substring(messageContent.indexOf("\n")+1));
//                            prices.add(messageContent.substring(messageContent.indexOf(":")+1,messageContent.indexOf("\n")));
                            prices.add(messageContent.substring(messageContent.indexOf(":")+1));
                        }
                        ans[0] = ans[0].substring(0, ans[0].length() - 1);
                        runOnRes.run();
                    }
                });
    }

    public void updateConversations(conversationsAdapter adapter, ArrayList<String> ids) {
        firestore.collection("users")
                .document(FirebaseAuth.getInstance().getUid())
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        List<String> arr = (List<String>) value.get("sessions");
                        if (arr == null) {
                            return;
                        }
                        ids.clear();
                        ids.addAll(arr);
                        adapter.notifyDataSetChanged();
                    }
                });
    }
}
