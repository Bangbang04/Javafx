/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.todo;


import javafx.beans.property.*;

public class Todo {
    private IntegerProperty id;
    private StringProperty judul;
    private StringProperty deskripsi;
    private BooleanProperty selesai;
    private StringProperty tanggalDibuat;
    private IntegerProperty userId;

    public Todo(int id, String judul, String deskripsi, boolean selesai, String tanggalDibuat, int userId) {
        this.id = new SimpleIntegerProperty(id);
        this.judul = new SimpleStringProperty(judul);
        this.deskripsi = new SimpleStringProperty(deskripsi);
        this.selesai = new SimpleBooleanProperty(selesai);
        this.tanggalDibuat = new SimpleStringProperty(tanggalDibuat);
        this.userId = new SimpleIntegerProperty(userId);
    }

    // Getter dan Setter untuk id
    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    // Getter dan Setter untuk judul
    public String getJudul() {
        return judul.get();
    }

    public void setJudul(String judul) {
        this.judul.set(judul);
    }

    public StringProperty judulProperty() {
        return judul;
    }

    // Getter dan Setter untuk deskripsi
    public String getDeskripsi() {
        return deskripsi.get();
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi.set(deskripsi);
    }

    public StringProperty deskripsiProperty() {
        return deskripsi;
    }

    // Getter dan Setter untuk selesai
    public boolean isSelesai() {
        return selesai.get();
    }

    public void setSelesai(boolean selesai) {
        this.selesai.set(selesai);
    }

    public BooleanProperty selesaiProperty() {
        return selesai;
    }

    // Getter dan Setter untuk tanggalDibuat
    public String getTanggalDibuat() {
        return tanggalDibuat.get();
    }

    public void setTanggalDibuat(String tanggalDibuat) {
        this.tanggalDibuat.set(tanggalDibuat);
    }

    public StringProperty tanggalDibuatProperty() {
        return tanggalDibuat;
    }

    // Getter dan Setter untuk userId
    public int getUserId() {
        return userId.get();
    }

    public void setUserId(int userId) {
        this.userId.set(userId);
    }

    public IntegerProperty userIdProperty() {
        return userId;
    }
}